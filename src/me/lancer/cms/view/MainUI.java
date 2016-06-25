package me.lancer.cms.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import me.lancer.cms.model.Employee;
import me.lancer.cms.service.EmployeeSrv;

@SuppressWarnings("serial")
public class MainUI extends JPanel {

	private int rst = -1;

	public MainUI() {
		super(new GridLayout(1, 1));

		LoginDialog loginDialog = new LoginDialog();
		loginDialog.toFront();
		loginDialog.setModal(true);
		loginDialog.setVisible(true);

		if (getRst() != -1) {
			JTabbedPane tp = new JTabbedPane();
			tp.setFont(new Font("微软雅黑", Font.PLAIN, 18));

			StudioUI panel1 = new StudioUI();
			tp.addTab("演出厅管理", null, panel1, "");
			tp.setMnemonicAt(0, KeyEvent.VK_1);

			SeatUI panel2 = new SeatUI(getRst());
			tp.addTab("座位管理", null, panel2, "");
			tp.setMnemonicAt(1, KeyEvent.VK_2);

			PlayUI panel3 = new PlayUI();
			tp.addTab("剧目管理", null, panel3, "");
			tp.setMnemonicAt(2, KeyEvent.VK_3);

			ScheduleUI panel4 = new ScheduleUI();
			tp.addTab("演出计划管理", null, panel4, "");
			tp.setMnemonicAt(3, KeyEvent.VK_4);

			SaleUI panel5 = new SaleUI();
			tp.addTab("订单管理", null, panel5, "");
			tp.setMnemonicAt(4, KeyEvent.VK_5);

			EmployeeUI panel6 = new EmployeeUI(getRst());
			tp.addTab("员工管理", null, panel6, "");
			tp.setMnemonicAt(5, KeyEvent.VK_6);

			DataDictUI panel7 = new DataDictUI();
			tp.addTab("数据字典管理", null, panel7, "");
			tp.setMnemonicAt(6, KeyEvent.VK_7);

			SettingUI panel8 = new SettingUI(getRst());
			tp.addTab("设置", null, panel8, "");
			tp.setMnemonicAt(7, KeyEvent.VK_8);

			tp.setPreferredSize(new Dimension(800, 600));
			tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tp.setTabPlacement(JTabbedPane.NORTH);
			tp.setSelectedIndex(1);
			add(tp);
		} else if (getRst() == -1) {
			JOptionPane.showMessageDialog(null, "用户名或密码错误!");
			System.exit(0);
		} else if (getRst() == -2) {
			JOptionPane.showMessageDialog(null, "用户名或密码不能为空!");
			System.exit(0);
		}
	}

	public static void createAndShowGUI() {
		ImageIcon bg = new ImageIcon("image/bg.jpg");
		JLabel label = new JLabel(bg);
		label.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
		JFrame frame = new JFrame("影院管理系统");
		frame.getLayeredPane().setLayout(null);
		frame.setUndecorated(true);
		frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setMinimumSize(new Dimension(1024, 768));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MainUI());
		frame.pack();
		frame.setOpacity(0.8f);
		frame.setVisible(true);
	}

	class LoginDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		private int frmWidth = 400;
		private int frmHeight = 400;
		private JPanel pan = new JPanel();
		private JLabel lblUsrName, lblPassWord;
		private JTextField txtUsrName;
		private JPasswordField txtPassWord;
		private JButton btnYes, btnNot;

		LoginDialog() {
			this.setTitle("欢迎使用影院管理系统");
			this.setSize(frmWidth, frmHeight);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			lblUsrName = new JLabel("用户名 : ");
			lblUsrName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblUsrName.setBounds(80, 65, 120, 30);
			pan.add(lblUsrName);
			txtUsrName = new JTextField();
			txtUsrName.setBounds(80, 100, 240, 30);
			pan.add(txtUsrName);

			lblPassWord = new JLabel("密码 : ");
			lblPassWord.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblPassWord.setBounds(80, 135, 120, 30);
			pan.add(lblPassWord);
			txtPassWord = new JPasswordField();
			txtPassWord.setBounds(80, 170, 240, 30);
			pan.add(txtPassWord);

			btnYes = new JButton("确认");
			btnYes.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnYes.setBounds(40, frmHeight - 80, 66, 30);
			btnYes.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void actionPerformed(ActionEvent e) {
					if (txtUsrName.getText().length() > 0 && txtPassWord.getText().length() > 0) {
						List<Employee> empList = new EmployeeSrv()
								.Fetch("(emp_no=" + txtUsrName.getText() + " or " + "emp_name='" + txtUsrName.getText()
										+ "') and " + "emp_password='" + txtPassWord.getText() + "'");
						if (empList.size() > 0) {
							setRst(empList.get(0).getId());
						} else {
							setRst(-1);
						}
					} else {
						setRst(-2);
					}
					dispose();
				}

			});
			pan.add(btnYes);

			btnNot = new JButton("取消");
			btnNot.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnNot.setBounds(frmWidth - 106, frmHeight - 80, 66, 30);
			btnNot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}

			});
			pan.add(btnNot);

			pan.setBounds(0, 0, frmWidth, this.frmHeight);
			pan.setLayout(null);
			this.add(pan);
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
				createAndShowGUI();
			}
		});
	}

	public int getRst() {
		return rst;
	}

	public void setRst(int rst) {
		this.rst = rst;
	}
}