package me.lancer.cms.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import me.lancer.cms.model.Play;
import me.lancer.cms.model.Schedule;
import me.lancer.cms.model.Studio;
import me.lancer.cms.service.PlaySrv;
import me.lancer.cms.service.ScheduleSrv;
import me.lancer.cms.service.StudioSrv;

class ScheduleTableMouseListener extends MouseAdapter {

	private JTable table;
	private static Schedule sched;

	public Schedule getSchedule() {
		return sched;
	}

	@SuppressWarnings("static-access")
	public ScheduleTableMouseListener(JTable table, Object[] number, Schedule sched) {
		this.sched = sched;
		this.table = table;
	}

	public void mouseClicked(MouseEvent event) {
		int row = table.getSelectedRow();
		sched.setId(Integer.parseInt(table.getValueAt(row, 0).toString()));
		sched.setStudioId(new StudioSrv().Fetch("studio_name='" + table.getValueAt(row, 1) + "'").get(0).getID());
		sched.setPlayId(new PlaySrv().Fetch("play_name='" + table.getValueAt(row, 2) + "'").get(0).getId());
		sched.setTime(Timestamp.valueOf(table.getValueAt(row, 3).toString()));
		sched.setPrice(Double.parseDouble(table.getValueAt(row, 4).toString()));
	}
}

class ScheduleTable {

	private JTable table = null;
	private Schedule sched;

	public ScheduleTable(Schedule sched) {
		this.sched = sched;
	}

	public void createTable(JScrollPane scrollPane, Object[] columnNames, List<Schedule> stuList) {
		try {
			Object data[][] = new Object[stuList.size()][columnNames.length];
			Iterator<Schedule> itr = stuList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Schedule item = itr.next();
				data[i][0] = Integer.toString(item.getId());
				data[i][1] = new StudioSrv().Fetch("studio_id=" + item.getStudioId()).get(0).getName();
				data[i][2] = new PlaySrv().Fetch("play_id=" + item.getPlayId()).get(0).getName();
				data[i][3] = item.getTime().toString();
				data[i][4] = Double.toString(item.getPrice());
				i++;
			}
			table = new JTable(data, columnNames);
			table.setRowHeight(24);
			table.getTableHeader().setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			table.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 14));
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, r);
			table.setBounds(0, 0, 800, 600);
			ScheduleTableMouseListener tml = new ScheduleTableMouseListener(table, columnNames, sched);
			table.addMouseListener(tml);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			scrollPane.add(table);
			scrollPane.setViewportView(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class ScheduleUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Schedule sched = new Schedule();
	private JScrollPane scrollPane;
	private JPanel btnList;
	private JButton btnAdd, btnQuery, btnEdit, btnDel;
	List<Schedule> rst = new ArrayList<>();

	public ScheduleUI() {
		super(new BorderLayout());
		initContent();
	}

	protected void initContent() {
		btnList = new JPanel();
		btnAdd = new JButton("Ìí¼Ó");
		btnAdd.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleDialog schedDialog = new ScheduleDialog(1);
				schedDialog.toFront();
				schedDialog.setModal(true);
				schedDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnAdd);

		btnQuery = new JButton("²éÕÒ");
		btnQuery.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleDialog schedDialog = new ScheduleDialog(2);
				schedDialog.toFront();
				schedDialog.setModal(true);
				schedDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnQuery);

		btnEdit = new JButton("ÐÞ¸Ä");
		btnEdit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleDialog schedDialog = new ScheduleDialog(3);
				schedDialog.toFront();
				schedDialog.setModal(true);
				schedDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnEdit);

		btnDel = new JButton("É¾³ý");
		btnDel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScheduleDialog schedDialog = new ScheduleDialog(4);
				schedDialog.toFront();
				schedDialog.setModal(true);
				schedDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnDel);
		
		this.add(btnList, BorderLayout.SOUTH);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(8000, 6000));
		this.add(scrollPane, BorderLayout.NORTH);
		showTable();
	}

	public void showTable() {
		ScheduleTable schedTable = new ScheduleTable(sched);
		Object[] in = { "ID", "ÑÝ³öÌü", "¾çÄ¿", "·ÅÓ³Ê±¼ä", "¼Û¸ñ" };
		List<Schedule> stuList = new ScheduleSrv().FetchAll();
		if (rst.size() > 0) {
			stuList = rst;
		}
		schedTable.createTable(scrollPane, in, stuList);
		scrollPane.repaint();
		this.repaint();
	}

	public static void showPanel() {
		JFrame frame = new JFrame("ÑÝ³ö¼Æ»®¹ÜÀí");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new ScheduleUI());
		frame.pack();
		frame.setVisible(true);
	}

	class ScheduleDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		final int flag;
		private int width = 400;
		private int height = 300;
		private List<String> studList = new ArrayList<>();
		private List<String> playList = new ArrayList<>();
		private JPanel pan = new JPanel();
		private JComboBox<String> cbxStudio, cbxPlay;
		private JLabel lblStudio, lblPlay, lblTime, lblPrice;
		private JTextField txtTime, txtPrice;
		private JButton btnYes, btnNot;
		private ArrayListComboBoxModel model1, model2;

		@SuppressWarnings("unchecked")
		ScheduleDialog(final int flag) {
			this.flag = flag;
			this.setTitle("ÑÝ³ö¼Æ»®²Ù×÷");
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			lblStudio = new JLabel("ÑÝ³öÌü");
			lblStudio.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblStudio.setBounds(80, 30, 60, 30);
			pan.add(lblStudio);
			for (Studio item : new StudioSrv().FetchAll()) {
				studList.add(item.getName());
			}
			model1 = new ArrayListComboBoxModel((ArrayList<String>) studList);
			cbxStudio = new JComboBox<String>(model1);
			cbxStudio.setBounds(140, 30, 120, 30);
			pan.add(cbxStudio);

			lblPlay = new JLabel("¾çÄ¿ : ");
			lblPlay.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblPlay.setBounds(80, 65, 60, 30);
			pan.add(lblPlay);
			for (Play item : new PlaySrv().FetchAll()) {
				playList.add(item.getName());
			}
			model2 = new ArrayListComboBoxModel((ArrayList<String>) playList);
			cbxPlay = new JComboBox<String>(model2);
			cbxPlay.setBounds(140, 65, 120, 30);
			pan.add(cbxPlay);

			lblTime = new JLabel("Ê±¼ä: ");
			lblTime.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblTime.setBounds(80, 100, 60, 30);
			pan.add(lblTime);

			txtTime = new JTextField();
			txtTime.setBounds(140, 100, 120, 30);
			pan.add(txtTime);

			lblPrice = new JLabel("¼Û¸ñ : ");
			lblPrice.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblPrice.setBounds(80, 135, 60, 30);
			pan.add(lblPrice);

			txtPrice = new JTextField();
			txtPrice.setBounds(140, 135, 120, 30);
			pan.add(txtPrice);

			if (flag == 3 || flag == 4) {
				cbxStudio.setSelectedItem(new StudioSrv().Fetch("studio_id=" + sched.getStudioId()));
				cbxPlay.setSelectedItem(new PlaySrv().Fetch("play_id=" + sched.getPlayId()));
				txtTime.setText(sched.getTime().toString());
				txtPrice.setText(Double.toString(sched.getPrice()));
			}

			btnYes = new JButton("È·ÈÏ");
			btnYes.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			btnYes.setBounds(40, height - 80, 66, 30);
			btnYes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (flag == 1) {
						btnAddClicked();
					} else if (flag == 2) {
						btnQueryClicked();
					} else if (flag == 3) {
						btnModClicked();
					} else if (flag == 4) {
						btnDelClicked();
					}
					dispose();
				}

			});
			pan.add(btnYes);

			btnNot = new JButton("È¡Ïû");
			btnNot.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			btnNot.setBounds(width - 106, height - 80, 66, 30);
			btnNot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}

			});
			pan.add(btnNot);

			pan.setBounds(0, 0, width, this.height);
			pan.setLayout(null);
			this.add(pan);
		}

		private void btnAddClicked() {
			if (cbxStudio.getSelectedItem() != null && cbxPlay.getSelectedItem() != null
					&& txtTime.getText().length() > 0 && txtPrice.getText().length() > 0) {
				Schedule sched = new Schedule();
				sched.setStudioId(
						new StudioSrv().Fetch("studio_name='" + cbxStudio.getSelectedItem() + "'").get(0).getID());
				sched.setPlayId(new PlaySrv().Fetch("play_name='" + cbxPlay.getSelectedItem() + "'").get(0).getId());
				sched.setTime(Timestamp.valueOf(txtTime.getText()));
				sched.setPrice(Double.parseDouble(txtPrice.getText()));
				new ScheduleSrv().add(sched);
			} else {
				JOptionPane.showMessageDialog(null, "Êý¾Ý²»ÍêÕû");
			}
		}

		private void btnQueryClicked() {
			Schedule sched = new Schedule();
			String sql = "";
			if (cbxStudio.getSelectedItem() != null) {
				sched.setStudioId(
						new StudioSrv().Fetch("studio_name='" + cbxStudio.getSelectedItem() + "'").get(0).getID());
				if (sql.equals("")) {
					sql += " studio_id="
							+ new StudioSrv().Fetch("studio_name='" + cbxStudio.getSelectedItem() + "'").get(0).getID();
				} else {
					sql += " and studio_id="
							+ new StudioSrv().Fetch("studio_name='" + cbxStudio.getSelectedItem() + "'").get(0).getID();
				}
			}
			if (cbxPlay.getSelectedItem() != null) {
				sched.setPlayId(new PlaySrv().Fetch("play_name='" + cbxPlay.getSelectedItem() + "'").get(0).getId());
				if (sql.equals("")) {
					sql += " play_id="
							+ new PlaySrv().Fetch("play_name='" + cbxPlay.getSelectedItem() + "'").get(0).getId();
				} else {
					sql += " and play_id="
							+ new PlaySrv().Fetch("play_name='" + cbxPlay.getSelectedItem() + "'").get(0).getId();
				}
			}
			if (txtTime.getText().length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					sched.setTime(sdf.parse(txtTime.getText()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (sql.equals("")) {
					sql += " sched_time='" + txtTime.getText() + "'";
				} else {
					sql += " and sched_time='" + txtTime.getText() + "'";
				}
			}
			if (txtPrice.getText().length() > 0) {
				sched.setPrice(Double.parseDouble(txtPrice.getText()));
				if (sql.equals("")) {
					sql += " sched_ticket_price=" + txtPrice.getText();
				} else {
					sql += " and sched_ticket_price=" + txtPrice.getText();
				}
			}
			rst = new ScheduleSrv().Fetch(sql);
		}

		private void btnModClicked() {
			Schedule sched = new Schedule();
			sched.setStudioId(
					new StudioSrv().Fetch("studio_name='" + cbxStudio.getSelectedItem() + "'").get(0).getID());
			sched.setPlayId(new PlaySrv().Fetch("play_name='" + cbxPlay.getSelectedItem() + "'").get(0).getId());
			sched.setTime(Timestamp.valueOf(txtTime.getText()));
			sched.setPrice(Double.parseDouble(txtPrice.getText()));
			new ScheduleSrv().modify(sched);
		}

		private void btnDelClicked() {
			int confirm = JOptionPane.showConfirmDialog(null, "È·ÈÏÉ¾³ýËùÑ¡£¿", "É¾³ý", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				StudioSrv stuSrv = new StudioSrv();
				stuSrv.delete(sched.getId());
				showTable();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	class ArrayListComboBoxModel extends AbstractListModel implements ComboBoxModel {

		private static final long serialVersionUID = 1L;
		private Object selectedItem;
		private ArrayList anArrayList;

		public ArrayListComboBoxModel(ArrayList<String> arrayList) {
			anArrayList = arrayList;
		}

		public Object getSelectedItem() {
			return selectedItem;
		}

		public void setSelectedItem(Object newValue) {
			selectedItem = newValue;
		}

		public int getSize() {
			return anArrayList.size();
		}

		public Object getElementAt(int i) {
			return anArrayList.get(i);
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showPanel();
			}
		});
	}
}
