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

import javax.swing.JButton;
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

import me.lancer.cms.model.Studio;
import me.lancer.cms.service.StudioSrv;

class StudioTableMouseListener extends MouseAdapter {

	private JTable table;
	private static Studio stud;

	public Studio getStudio() {
		return stud;
	}

	@SuppressWarnings("static-access")
	public StudioTableMouseListener(JTable table, Object[] number, Studio stud) {
		this.table = table;
		this.stud = stud;
	}

	public void mouseClicked(MouseEvent event) {
		int row = table.getSelectedRow();
		stud.setID(Integer.parseInt(table.getValueAt(row, 0).toString()));
		stud.setName(table.getValueAt(row, 1).toString());
		stud.setRowCount(Integer.parseInt(table.getValueAt(row, 2).toString())); // 0
		stud.setColCount(Integer.parseInt(table.getValueAt(row, 3).toString()));
		stud.setIntroduction(table.getValueAt(row, 4).toString());
	}
}

class StudioTable {

	private JTable table = null;
	private Studio stud;

	public StudioTable(Studio stud) {
		this.stud = stud;
	}

	public void createTable(JScrollPane scrollPane, Object[] columnNames, List<Studio> stuList) {
		try {
			Object data[][] = new Object[stuList.size()][columnNames.length];
			Iterator<Studio> itr = stuList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Studio item = itr.next();
				data[i][0] = Integer.toString(item.getID());
				data[i][1] = item.getName();
				data[i][2] = Integer.toString(item.getRowCount());
				data[i][3] = Integer.toString(item.getColCount());
				data[i][4] = item.getIntroduction();
				data[i][5] = item.getStudioFlag();
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
			StudioTableMouseListener tml = new StudioTableMouseListener(table, columnNames, stud);
			table.addMouseListener(tml);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			scrollPane.add(table);
			scrollPane.setViewportView(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setFont(Font font) {
	}
}

public class StudioUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Studio stud = new Studio();
	private JScrollPane scrollPane;
	private JPanel btnList;
	private JButton btnAdd, btnQuery, btnEdit, btnDel;
	List<Studio> rst = new ArrayList<>();

	public StudioUI() {
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
				StudioDialog seatDialog = new StudioDialog(1);
				seatDialog.toFront();
				seatDialog.setModal(true);
				seatDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnAdd);
		
		btnQuery = new JButton("²éÕÒ");
		btnQuery.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StudioDialog seatDialog = new StudioDialog(2);
				seatDialog.toFront();
				seatDialog.setModal(true);
				seatDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnQuery);
		
		btnEdit = new JButton("ÐÞ¸Ä");
		btnEdit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StudioDialog seatDialog = new StudioDialog(3);
				seatDialog.toFront();
				seatDialog.setModal(true);
				seatDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnEdit);
		
		btnDel = new JButton("É¾³ý");
		btnDel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StudioDialog seatDialog = new StudioDialog(4);
				seatDialog.toFront();
				seatDialog.setModal(true);
				seatDialog.setVisible(true);
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
		StudioTable studTable = new StudioTable(stud);
		studTable.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		Object[] in = { "ID", "Ãû×Ö", "ÐÐÊý", "ÁÐÊý", "ÃèÊö", "×´Ì¬" };
		List<Studio> stuList = new StudioSrv().FetchAll();
		if (rst.size() > 0) {
			stuList = rst;
		}
		studTable.createTable(scrollPane, in, stuList);
		scrollPane.repaint();
		this.repaint();
	}

	public static void showPanel() {
		JFrame frame = new JFrame("ÑÝ³öÌü¹ÜÀí");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new StudioUI());
		frame.pack();
		frame.setVisible(true);
	}

	class StudioDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		final int flag;
		private int width = 400;
		private int height = 300;
		private JPanel pan = new JPanel();
		private JLabel lblName, lblRow, lblCol, lblIntroduction, lblFlag;
		private JTextField txtName, txtRow, txtCol, txtIntroduction, txtFlag;
		private JButton btnYes, btnNot;

		StudioDialog(final int flag) {
			this.flag = flag;
			this.setTitle("ÑÝ³öÌü²Ù×÷");
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			lblName = new JLabel("Ãû×Ö : ");
			lblName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblName.setBounds(80, 30, 60, 30);
			pan.add(lblName);
			txtName = new JTextField();
			txtName.setBounds(140, 30, 120, 30);
			pan.add(txtName);

			lblRow = new JLabel("ÐÐÊý : ");
			lblRow.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblRow.setBounds(80, 65, 60, 30);
			pan.add(lblRow);
			txtRow = new JTextField();
			txtRow.setBounds(140, 65, 120, 30);
			pan.add(txtRow);

			lblCol = new JLabel("ÁÐÊý : ");
			lblCol.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblCol.setBounds(80, 100, 60, 30);
			pan.add(lblCol);
			txtCol = new JTextField();
			txtCol.setBounds(140, 100, 120, 30);
			pan.add(txtCol);

			lblIntroduction = new JLabel("ÃèÊö : ");
			lblIntroduction.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblIntroduction.setBounds(80, 135, 60, 30);
			pan.add(lblIntroduction);
			txtIntroduction = new JTextField();
			txtIntroduction.setBounds(140, 135, 120, 30);
			pan.add(txtIntroduction);

			lblFlag = new JLabel("×´Ì¬ : ");
			lblFlag.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
			lblFlag.setBounds(80, 170, 60, 30);
			pan.add(lblFlag);
			txtFlag = new JTextField();
			txtFlag.setBounds(140, 170, 120, 30);
			pan.add(txtFlag);

			if (flag == 3 || flag == 4) {
				txtName.setText(stud.getName());
				txtRow.setText("" + stud.getRowCount());
				txtCol.setText("" + stud.getColCount());
				txtIntroduction.setText(stud.getIntroduction());
				txtFlag.setText("" + stud.getStudioFlag());
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
			if (txtName.getText().length() > 0 && txtRow.getText().length() > 0 && txtCol.getText().length() > 0
					&& txtIntroduction.getText().length() > 0) {
				Studio stud = new Studio();
				stud.setName(txtName.getText());
				stud.setRowCount(Integer.parseInt(txtRow.getText()));
				stud.setColCount(Integer.parseInt(txtCol.getText()));
				stud.setIntroduction(txtIntroduction.getText());
				stud.setStudioFlag(0);
				new StudioSrv().add(stud);
			} else {
				JOptionPane.showMessageDialog(null, "Êý¾Ý²»ÍêÕû");
			}
		}

		private void btnQueryClicked() {
			Studio stud = new Studio();
			String sql = "";
			if (txtName.getText().length() > 0) {
				stud.setName(txtName.getText());
				if (sql.equals("")) {
					sql += " studio_name='" + txtName.getText() + "'";
				} else {
					sql += " and studio_name='" + txtName.getText() + "'";
				}
			}
			if (txtRow.getText().length() > 0) {
				stud.setRowCount(Integer.parseInt(txtRow.getText()));
				if (sql.equals("")) {
					sql += " studio_row_count=" + txtRow.getText();
				} else {
					sql += " and studio_row_count=" + txtRow.getText();
				}
			}
			if (txtCol.getText().length() > 0) {
				stud.setColCount(Integer.parseInt(txtCol.getText()));
				if (sql.equals("")) {
					sql += " studio_col_count=" + txtCol.getText();
				} else {
					sql += " and studio_col_count=" + txtCol.getText();
				}
			}
			if (txtIntroduction.getText().length() > 0) {
				stud.setIntroduction(txtIntroduction.getText());
				if (sql.equals("")) {
					sql += " studio_introduction='" + txtIntroduction.getText() + "'";
				} else {
					sql += " and studio_introduction='" + txtIntroduction.getText() + "'";
				}
			}
			if (txtFlag.getText().length() > 0) {
				stud.setStudioFlag(Integer.parseInt(txtFlag.getText()));
				if (sql.equals("")) {
					sql += " studio_flag=" + txtFlag.getText();
				} else {
					sql += " and studio_flag=" + txtFlag.getText();
				}
			}
			rst = new StudioSrv().Fetch(sql);
		}

		private void btnModClicked() {
			stud.setName(txtName.getText());
			stud.setRowCount(Integer.parseInt(txtRow.getText()));
			stud.setColCount(Integer.parseInt(txtCol.getText()));
			stud.setIntroduction(txtIntroduction.getText());
			stud.setStudioFlag(Integer.parseInt(txtFlag.getText()));
			new StudioSrv().modify(stud);
		}

		private void btnDelClicked() {
			int confirm = JOptionPane.showConfirmDialog(null, "È·ÈÏÉ¾³ýËùÑ¡£¿", "É¾³ý", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				StudioSrv stuSrv = new StudioSrv();
				stuSrv.delete(stud.getID());
				showTable();
			}
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
