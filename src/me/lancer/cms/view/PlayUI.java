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

import me.lancer.cms.model.DataDict;
import me.lancer.cms.model.Play;
import me.lancer.cms.service.DataDictSrv;
import me.lancer.cms.service.PlaySrv;

class PlayTableMouseListener extends MouseAdapter {

	private JTable table;
	private Play play;

	public Play getPlay() {
		return play;
	}

	public PlayTableMouseListener(JTable table, Object[] number, Play play) {
		this.play = play;
		this.table = table;
	}

	public void mouseClicked(MouseEvent event) {
		int row = table.getSelectedRow();
		play.setId(Integer.parseInt(table.getValueAt(row, 0).toString()));
		play.setTypeId(new DataDictSrv().Fetch("dict_name='" + table.getValueAt(row, 1) + "'").get(0).getId());
		play.setLangId(new DataDictSrv().Fetch("dict_name='" + table.getValueAt(row, 2) + "'").get(0).getId());
		play.setName(table.getValueAt(row, 3).toString());
		play.setIntroduction(table.getValueAt(row, 4).toString());
		play.setLength(Integer.parseInt(table.getValueAt(row, 5).toString()));
		play.setPrice(Float.parseFloat(table.getValueAt(row, 6).toString()));
		play.setStatus(Integer.parseInt(table.getValueAt(row, 7).toString()));
	}
}

class PlayTable {

	private JTable table = null;
	private Play play;

	public PlayTable(Play play) {
		this.play = play;
	}

	public void createTable(JScrollPane scrollPane, Object[] columnNames, List<Play> stuList) {
		try {
			Object data[][] = new Object[stuList.size()][columnNames.length];
			Iterator<Play> itr = stuList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Play item = itr.next();
				data[i][0] = Integer.toString(item.getId());
				data[i][1] = new DataDictSrv().Fetch("dict_id=" + item.getTypeId()).get(0).getName();
				data[i][2] = new DataDictSrv().Fetch("dict_id=" + item.getLangId()).get(0).getName();
				data[i][3] = item.getName();
				data[i][4] = item.getIntroduction();
				data[i][5] = Integer.toString(item.getLength());
				data[i][6] = Float.toString(item.getPrice());
				data[i][7] = Integer.toString(item.getStatus());
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
			PlayTableMouseListener tml = new PlayTableMouseListener(table, columnNames, play);
			table.addMouseListener(tml);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			scrollPane.add(table);
			scrollPane.setViewportView(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class PlayUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Play ply = new Play();
	private JScrollPane scrollPane;
	private JPanel btnList;
	private JButton btnAdd, btnQuery, btnEdit, btnDel;
	List<Play> rst = new ArrayList<>();

	public PlayUI() {
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
				PlayDialog playDialog = new PlayDialog(1);
				playDialog.toFront();
				playDialog.setModal(true);
				playDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnAdd);

		btnQuery = new JButton("²éÕÒ");
		btnQuery.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayDialog playDialog = new PlayDialog(2);
				playDialog.toFront();
				playDialog.setModal(true);
				playDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnQuery);

		btnEdit = new JButton("ÐÞ¸Ä");
		btnEdit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayDialog playDialog = new PlayDialog(3);
				playDialog.toFront();
				playDialog.setModal(true);
				playDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnEdit);

		btnDel = new JButton("É¾³ý");
		btnDel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayDialog playDialog = new PlayDialog(4);
				playDialog.toFront();
				playDialog.setModal(true);
				playDialog.setVisible(true);
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
		PlayTable playTable = new PlayTable(ply);
		Object[] in = { "ID", "ÀàÐÍ", "ÓïÑÔ", "Ó°Ãû", "ÃèÊö", "Ê±³¤", "¼Û¸ñ", "×´Ì¬" };
		List<Play> playList = new PlaySrv().FetchAll();
		if (rst.size() > 0) {
			playList = rst;
		}
		playTable.createTable(scrollPane, in, playList);
		scrollPane.repaint();
		this.repaint();
	}

	public static void showPanel() {
		JFrame frame = new JFrame("¾çÄ¿¹ÜÀí");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new PlayUI());
		frame.pack();
		frame.setVisible(true);
	}

	class PlayDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		final int flag;
		private int width = 400;
		private int height = 400;
		private List<String> typeList = new ArrayList<>();
		private List<String> langList = new ArrayList<>();
		private JPanel pan = new JPanel();
		private JComboBox<String> cbxType, cbxLang;
		private JLabel lblType, lblLang, lblName, lblIntroduction, lblLength, lblPrice, lblStatus;
		private JTextField txtName, txtIntroduction, txtLength, txtPrice, txtStatus;
		private JButton btnYes, btnNot;
		private ArrayListComboBoxModel model1, model2;

		@SuppressWarnings("unchecked")
		PlayDialog(final int flag) {
			this.flag = flag;
			this.setTitle("¾çÄ¿²Ù×÷");
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			lblType = new JLabel("ÀàÐÍ : ");
			lblType.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblType.setBounds(80, 30, 60, 30);
			pan.add(lblType);
			for (DataDict item : new DataDictSrv().Fetch("dict_parent_id=2")) {
				typeList.add(item.getName());
			}
			model1 = new ArrayListComboBoxModel((ArrayList<String>) typeList);
			cbxType = new JComboBox<String>(model1);
			cbxType.setBounds(140, 30, 120, 30);
			pan.add(cbxType);

			lblLang = new JLabel("ÓïÑÔ : ");
			lblLang.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblLang.setBounds(80, 65, 60, 30);
			pan.add(lblLang);
			for (DataDict item : new DataDictSrv().Fetch("dict_parent_id=3")) {
				langList.add(item.getName());
			}
			model2 = new ArrayListComboBoxModel((ArrayList<String>) langList);
			cbxLang = new JComboBox<String>(model2);
			cbxLang.setBounds(140, 65, 120, 30);
			pan.add(cbxLang);

			lblName = new JLabel("Ãû×Ö : ");
			lblName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblName.setBounds(80, 100, 60, 30);
			pan.add(lblName);
			txtName = new JTextField();
			txtName.setBounds(140, 100, 120, 30);
			pan.add(txtName);

			lblIntroduction = new JLabel("ÃèÊö : ");
			lblIntroduction.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblIntroduction.setBounds(80, 135, 60, 30);
			pan.add(lblIntroduction);
			txtIntroduction = new JTextField();
			txtIntroduction.setBounds(140, 135, 120, 30);
			pan.add(txtIntroduction);

			lblLength = new JLabel("Ê±³¤ : ");
			lblLength.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblLength.setBounds(80, 170, 60, 30);
			pan.add(lblLength);
			txtLength = new JTextField();
			txtLength.setBounds(140, 170, 120, 30);
			pan.add(txtLength);

			lblPrice = new JLabel("¼Û¸ñ : ");
			lblPrice.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblPrice.setBounds(80, 205, 60, 30);
			pan.add(lblPrice);
			txtPrice = new JTextField();
			txtPrice.setBounds(140, 205, 120, 30);
			pan.add(txtPrice);

			lblStatus = new JLabel("×´Ì¬ : ");
			lblStatus.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblStatus.setBounds(80, 240, 60, 30);
			pan.add(lblStatus);
			txtStatus = new JTextField();
			txtStatus.setBounds(140, 240, 120, 30);
			pan.add(txtStatus);

			if (flag == 3 || flag == 4) {
				cbxType.setSelectedItem(new DataDictSrv().Fetch("dict_id=" + ply.getTypeId()));
				cbxLang.setSelectedItem(new DataDictSrv().Fetch("dict_id=" + ply.getLangId()));
				txtName.setText(ply.getName());
				txtIntroduction.setText(ply.getIntroduction());
				txtLength.setText("" + ply.getLength());
				txtPrice.setText("" + ply.getPrice());
				txtStatus.setText("" + ply.getStatus());
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
			if (cbxLang.getSelectedItem() != null && cbxType.getSelectedItem() != null && txtName.getText().length() > 0
					&& txtIntroduction.getText().length() > 0 && txtLength.getText().length() > 0
					&& txtPrice.getText().length() > 0 && txtStatus.getText().length() > 0) {
				Play ply = new Play();
				ply.setTypeId(new DataDictSrv().Fetch("dict_name='" + cbxType.getSelectedItem() + "'").get(0).getId());
				ply.setLangId(new DataDictSrv().Fetch("dict_name='" + cbxType.getSelectedItem() + "'").get(0).getId());
				ply.setName(txtName.getText());
				ply.setIntroduction(txtIntroduction.getText());
				ply.setLength(Integer.parseInt(txtLength.getText()));
				ply.setPrice(Float.parseFloat(txtPrice.getText()));
				ply.setStatus(Integer.parseInt(txtStatus.getText()));
				new PlaySrv().add(ply);
			} else {
				JOptionPane.showMessageDialog(null, "Êý¾Ý²»ÍêÕû");
			}
		}

		private void btnQueryClicked() {
			Play ply = new Play();
			String sql = "";
			if (cbxType.getSelectedItem() != null) {
				ply.setTypeId(new DataDictSrv().Fetch("dict_name='" + cbxType.getSelectedItem() + "'").get(0).getId());
				sql += " play_type_id="
						+ new DataDictSrv().Fetch("dict_name='" + cbxType.getSelectedItem() + "'").get(0).getId();
			}
			if (cbxLang.getSelectedItem() != null) {
				ply.setLangId(new DataDictSrv().Fetch("dict_name='" + cbxLang.getSelectedItem() + "'").get(0).getId());
				if (sql.equals("")) {
					sql += " play_lang_id="
							+ new DataDictSrv().Fetch("dict_name='" + cbxLang.getSelectedItem() + "'").get(0).getId();
				} else {
					sql += " and play_lang_id="
							+ new DataDictSrv().Fetch("dict_name='" + cbxLang.getSelectedItem() + "'").get(0).getId();
				}
			}
			if (txtName.getText().length() > 0) {
				ply.setName(txtName.getText());
				if (sql.equals("")) {
					sql += " play_name='" + txtName.getText() + "'";
				} else {
					sql += " and play_name='" + txtName.getText() + "'";
				}
			}
			if (txtIntroduction.getText().length() > 0) {
				ply.setIntroduction(txtIntroduction.getText());
				if (sql.equals("")) {
					sql += " play_introduction='" + txtIntroduction.getText() + "'";
				} else {
					sql += " and play_introduction='" + txtIntroduction.getText() + "'";
				}
			}
			if (txtLength.getText().length() > 0) {
				ply.setLength(Integer.parseInt(txtLength.getText()));
				if (sql.equals("")) {
					sql += " play_length=" + txtLength.getText();
				} else {
					sql += " and play_length=" + txtLength.getText();
				}
			}
			if (txtPrice.getText().length() > 0) {
				ply.setPrice(Float.parseFloat(txtPrice.getText()));
				if (sql.equals("")) {
					sql += " play_ticket_price=" + txtPrice.getText();
				} else {
					sql += " and play_ticket_price=" + txtPrice.getText();
				}
			}
			if (txtStatus.getText().length() > 0) {
				ply.setStatus(Integer.parseInt(txtStatus.getText()));
				if (sql.equals("")) {
					sql += " play_status=" + txtStatus.getText();
				} else {
					sql += " and play_status=" + txtStatus.getText();
				}
			}
			rst = new PlaySrv().Fetch(sql);
		}

		private void btnModClicked() {
			ply.setTypeId(new DataDictSrv().Fetch("dict_name='" + cbxType.getSelectedItem() + "'").get(0).getId());
			ply.setLangId(new DataDictSrv().Fetch("dict_name='" + cbxLang.getSelectedItem() + "'").get(0).getId());
			ply.setName(txtName.getText());
			ply.setIntroduction(txtIntroduction.getText());
			ply.setLength(Integer.parseInt(txtLength.getText()));
			ply.setPrice(Float.parseFloat(txtPrice.getText()));
			ply.setStatus(Integer.parseInt(txtStatus.getText()));
			new PlaySrv().modify(ply);
		}

		private void btnDelClicked() {
			int confirm = JOptionPane.showConfirmDialog(null, "È·ÈÏÉ¾³ýËùÑ¡£¿", "É¾³ý", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				PlaySrv stuSrv = new PlaySrv();
				stuSrv.delete(ply.getId());
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
