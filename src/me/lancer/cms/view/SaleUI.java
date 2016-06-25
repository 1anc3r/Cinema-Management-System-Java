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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import me.lancer.cms.model.Sale;
import me.lancer.cms.service.EmployeeSrv;
import me.lancer.cms.service.SaleSrv;

class SaleTableMouseListener extends MouseAdapter {

	private JTable table;
	private static Sale sale;

	public Sale getSale() {
		return sale;
	}

	@SuppressWarnings("static-access")
	public SaleTableMouseListener(JTable table, Object[] number, Sale sale) {
		this.sale = sale;
		this.table = table;
	}

	public void mouseClicked(MouseEvent event) {
		int row = table.getSelectedRow();
		sale.setId(Integer.parseInt(table.getValueAt(row, 0).toString()));
		sale.setEmpId(Integer.parseInt(table.getValueAt(row, 1).toString()));
		sale.setTime(Timestamp.valueOf(table.getValueAt(row, 2).toString()));
		sale.setPayment(Float.parseFloat(table.getValueAt(row, 3).toString()));
		sale.setChange(Float.parseFloat(table.getValueAt(row, 4).toString()));
		sale.setType(Integer.parseInt(table.getValueAt(row, 5).toString()));
		sale.setStatus(Integer.parseInt(table.getValueAt(row, 6).toString()));
	}
}

class SaleTable {

	private JTable table = null;
	private Sale sale;

	public SaleTable(Sale sale) {
		this.sale = sale;
	}

	public void createTable(JScrollPane scrollPane, Object[] columnNames, List<Sale> saleList) {
		try {
			Object data[][] = new Object[saleList.size()][columnNames.length];
			Iterator<Sale> itr = saleList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Sale item = itr.next();
				data[i][0] = Integer.toString(item.getId());
				data[i][1] = new EmployeeSrv().Fetch("emp_id=" + item.getEmpId()).get(0).getName();
				data[i][2] = item.getTime();
				data[i][3] = Float.toString(item.getPayment());
				data[i][4] = Float.toString(item.getChange());
				data[i][5] = Integer.toString(item.getType());
				data[i][6] = Integer.toString(item.getStatus());
				i++;
			}
			table = new JTable(data, columnNames);
			table.setRowHeight(24);
			table.getTableHeader().setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			table.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 14));
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			table.setDefaultRenderer(Object.class, r);
			table.setBounds(0, 0, 800, 600);
			SaleTableMouseListener tml = new SaleTableMouseListener(table, columnNames, sale);
			table.addMouseListener(tml);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			scrollPane.add(table);
			scrollPane.setViewportView(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class SaleUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Sale sale = new Sale();
	private JScrollPane scrollPane;
	private JPanel btnList;
	private JButton btnQuery, btnDel;
	List<Sale> rst = new ArrayList<>();

	public SaleUI() {
		super(new BorderLayout());
		initContent();
	}

	protected void initContent() {
		btnList = new JPanel();

		btnQuery = new JButton("≤È’“");
		btnQuery.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaleDialog saleDialog = new SaleDialog(1);
				saleDialog.toFront();
				saleDialog.setModal(true);
				saleDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnQuery);

		btnDel = new JButton("ÕÀ∆±");
		btnDel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaleDialog saleDialog = new SaleDialog(2);
				saleDialog.toFront();
				saleDialog.setModal(true);
				saleDialog.setVisible(true);
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
		SaleTable saleTable = new SaleTable(sale);
		Object[] in = { "ID", "‘±π§", " ±º‰", "∏∂øÓ", "’“¡„", "¿‡–Õ", "◊¥Ã¨" };
		List<Sale> saleList = new SaleSrv().FetchAll();
		if (rst.size() > 0) {
			saleList = rst;
		}
		saleTable.createTable(scrollPane, in, saleList);
		scrollPane.repaint();
		this.repaint();
	}

	public static void showPanel() {
		JFrame frame = new JFrame("∂©µ•π‹¿Ì");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SaleUI());
		frame.pack();
		frame.setVisible(true);
	}

	class SaleDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		final int flag;
		private int width = 400;
		private int height = 400;
		private JPanel pan = new JPanel();
		private JLabel lblEmployee, lblTime, lblPayment, lblChange, lblType, lblStatus;
		private JTextField txtEmployee, txtTime, txtPayment, txtChange, txtType, txtStatus;
		private JButton btnYes, btnNot;

		SaleDialog(final int flag) {
			this.flag = flag;
			this.setTitle("∂©µ•≤Ÿ◊˜");
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			lblEmployee = new JLabel("‘±π§");
			lblEmployee.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			lblEmployee.setBounds(80, 30, 60, 30);
			pan.add(lblEmployee);

			txtEmployee = new JTextField("");
			txtEmployee.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			txtEmployee.setBounds(140, 30, 120, 30);
			pan.add(txtEmployee);

			lblTime = new JLabel(" ±º‰");
			lblTime.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			lblTime.setBounds(80, 65, 60, 30);
			pan.add(lblTime);
			txtTime = new JTextField("");
			txtTime.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			txtTime.setBounds(140, 65, 120, 30);
			pan.add(txtTime);

			lblPayment = new JLabel("∏∂øÓ");
			lblPayment.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			lblPayment.setBounds(80, 100, 60, 30);
			pan.add(lblPayment);

			txtPayment = new JTextField("");
			txtPayment.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			txtPayment.setBounds(140, 100, 120, 30);
			pan.add(txtPayment);

			lblChange = new JLabel("’“¡„");
			lblChange.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			lblChange.setBounds(80, 135, 60, 30);
			pan.add(lblChange);

			txtChange = new JTextField("");
			txtChange.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			txtChange.setBounds(140, 135, 120, 30);
			pan.add(txtChange);

			lblType = new JLabel("¿‡–Õ");
			lblType.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			lblType.setBounds(80, 170, 60, 30);
			pan.add(lblType);

			txtType = new JTextField("");
			txtType.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			txtType.setBounds(140, 170, 120, 30);
			pan.add(txtType);

			lblStatus = new JLabel("◊¥Ã¨");
			lblStatus.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			lblStatus.setBounds(80, 205, 60, 30);
			pan.add(lblStatus);

			txtStatus = new JTextField("");
			txtStatus.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			txtStatus.setBounds(140, 205, 120, 30);
			pan.add(txtStatus);

			if (flag == 2) {
				txtEmployee.setText(new EmployeeSrv().Fetch("emp_id=" + sale.getEmpId()).get(0).getName());
				txtTime.setText(sale.getTime().toString());
				txtPayment.setText(Float.toString(sale.getPayment()));
				txtChange.setText(Float.toString(sale.getChange()));
				txtType.setText(Integer.toString(sale.getType()));
				txtStatus.setText(Integer.toString(sale.getStatus()));
			}

			btnYes = new JButton("»∑»œ");
			btnYes.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
			btnYes.setBounds(40, height - 80, 66, 30);
			btnYes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (flag == 1) {
						btnQueryClicked();
					} else if (flag == 2) {
						btnDelClicked();
					}
					dispose();
				}

			});
			pan.add(btnYes);

			btnNot = new JButton("»°œ˚");
			btnNot.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
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

		private void btnQueryClicked() {
			String sql = "";
			if (txtEmployee.getText().length() > 0) {
				sale.setEmpId(new EmployeeSrv().Fetch("emp_name='" + txtEmployee.getText() + "'").get(0).getId());
				if (sql.equals("")) {
					sql += " emp_id="
							+ new EmployeeSrv().Fetch("emp_name='" + txtEmployee.getText() + "'").get(0).getId();
				} else {
					sql += " and emp_id="
							+ new EmployeeSrv().Fetch("emp_name='" + txtEmployee.getText() + "'").get(0).getId();
				}
			}
			if (txtTime.getText().length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					sale.setTime(sdf.parse(txtTime.getText()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (sql.equals("")) {
					sql += " sale_time='" + txtTime.getText() + "'";
				} else {
					sql += " and sale_time='" + txtTime.getText() + "'";
				}
			}
			if (txtPayment.getText().length() > 0) {
				sale.setPayment(Float.parseFloat(txtPayment.getText()));
				if (sql.equals("")) {
					sql += " sale_payment=" + txtPayment.getText();
				} else {
					sql += " and sale_payment=" + txtPayment.getText();
				}
			}
			if (txtChange.getText().length() > 0) {
				sale.setChange(Float.parseFloat(txtChange.getText()));
				if (sql.equals("")) {
					sql += " sale_change=" + txtChange.getText();
				} else {
					sql += " and sale_change=" + txtChange.getText();
				}
			}
			if (txtType.getText().length() > 0) {
				sale.setType(Integer.parseInt(txtType.getText()));
				if (sql.equals("")) {
					sql += " sale_type=" + txtType.getText();
				} else {
					sql += " and sale_type=" + txtType.getText();
				}
			}
			if (txtStatus.getText().length() > 0) {
				sale.setStatus(Integer.parseInt(txtStatus.getText()));
				if (sql.equals("")) {
					sql += " sale_status=" + txtStatus.getText();
				} else {
					sql += " and sale_status=" + txtStatus.getText();
				}
			}
			System.out.println(sql);
			rst = new SaleSrv().Fetch(sql);
		}

		private void btnDelClicked() {
			sale.setEmpId(new EmployeeSrv().Fetch("emp_name=" + txtEmployee.getText()).get(0).getId());
			sale.setTime(Timestamp.valueOf(txtTime.getText()));
			sale.setStatus(-1);
			new SaleSrv().modify(sale);
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
