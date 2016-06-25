package me.lancer.cms.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import me.lancer.cms.model.Employee;
import me.lancer.cms.service.EmployeeSrv;

public class SettingUI extends JPanel {

	private static final long serialVersionUID = 1L;
	int empId;
	Employee emp;

	public SettingUI(int empId) {
		this.empId = empId;
		this.emp = new EmployeeSrv().Fetch("emp_id=" + empId).get(0);
		this.setLayout(new GridBagLayout());
		initContent();
	}

	protected void initContent() {
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);

		JButton btnUser = new JButton("≤Èø¥∏ˆ»À◊ ¡œ");
		btnUser.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UsrDialog usrDialog = new UsrDialog(1);
				usrDialog.toFront();
				usrDialog.setModal(true);
				usrDialog.setVisible(true);
			}

		});
		this.add(btnUser);

		JButton btnEdit = new JButton("–ﬁ∏ƒ∏ˆ»À◊ ¡œ");
		btnEdit.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UsrDialog usrDialog = new UsrDialog(2);
				usrDialog.toFront();
				usrDialog.setModal(true);
				usrDialog.setVisible(true);
			}

		});
		this.add(btnEdit);

		JButton btnHelp = new JButton("πÿ”⁄/∞Ô÷˙");
		btnHelp.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UsrDialog usrDialog = new UsrDialog(3);
				usrDialog.toFront();
				usrDialog.setModal(true);
				usrDialog.setVisible(true);
			}

		});
		this.add(btnHelp);

		JButton btnSwitch = new JButton("«–ªª’À∫≈");
		btnSwitch.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnSwitch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "∞•—Ω“ª≤ª–°–ƒ±ªŒ“◊¢ ÕµÙ¡À!");
			}

		});
		this.add(btnSwitch);

		JButton btnExit = new JButton("ÕÀ≥ˆœµÕ≥");
		btnExit.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		this.add(btnExit);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(100, 200, 25, 200);
		gbl.setConstraints(btnUser, gbc);
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(25, 200, 25, 200);
		gbl.setConstraints(btnEdit, gbc);
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(25, 200, 25, 200);
		gbl.setConstraints(btnHelp, gbc);
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(25, 200, 25, 200);
		gbl.setConstraints(btnSwitch, gbc);
		gbc.gridwidth = 0;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(25, 200, 100, 200);
		gbl.setConstraints(btnExit, gbc);
	}

	public static void showPanel() {
		JFrame frame = new JFrame("…Ë÷√");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SettingUI(1));
		frame.pack();
		frame.setVisible(true);
	}

	class UsrDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		int flag;
		private int width = 400;
		private int height = 400;
		private JPanel pan = new JPanel();
		private JLabel lblAccess1, lblName1, lblNo1, lblAddr1, lblTel1, lblEmail1;
		private JLabel lblAccess2, lblName2, lblNo2, lblAddr2, lblTel2, lblEmail2;
		private JComboBox<String> cbxAccess;
		private JLabel lblName, lblNo, lblPassWord, lblAddr, lblTel, lblEmail;
		private JTextField txtName, txtNo, txtAddr, txtTel, txtEmail;
		private JPasswordField txtPassWord;
		private JButton btnYes, btnNot;
		String accessList[] = { "‘±π§", "æ≠¿Ì", "π‹¿Ì‘±" };

		UsrDialog(int flag) {
			this.flag = flag;
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			if (flag == 1) {
				this.setTitle("≤Èø¥◊ ¡œ");

				lblAccess1 = new JLabel("»®œﬁ : ");
				lblAccess1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAccess1.setBounds(80, 30, 60, 30);
				pan.add(lblAccess1);
				lblAccess2 = new JLabel("");
				lblAccess2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAccess2.setBounds(140, 30, 240, 30);
				pan.add(lblAccess2);

				lblNo1 = new JLabel("π§∫≈ : ");
				lblNo1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblNo1.setBounds(80, 65, 60, 30);
				pan.add(lblNo1);
				lblNo2 = new JLabel("");
				lblNo2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblNo2.setBounds(140, 65, 240, 30);
				pan.add(lblNo2);

				lblName1 = new JLabel("√˚◊÷ : ");
				lblName1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblName1.setBounds(80, 100, 60, 30);
				pan.add(lblName1);
				lblName2 = new JLabel("");
				lblName2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblName2.setBounds(140, 100, 240, 30);
				pan.add(lblName2);

				lblAddr1 = new JLabel("µÿ÷∑ : ");
				lblAddr1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAddr1.setBounds(80, 135, 60, 30);
				pan.add(lblAddr1);
				lblAddr2 = new JLabel("");
				lblAddr2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAddr2.setBounds(140, 135, 240, 30);
				pan.add(lblAddr2);

				lblTel1 = new JLabel("µÁª∞ : ");
				lblTel1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblTel1.setBounds(80, 170, 60, 30);
				pan.add(lblTel1);
				lblTel2 = new JLabel();
				lblTel2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblTel2.setBounds(140, 170, 240, 30);
				pan.add(lblTel2);

				lblEmail1 = new JLabel("” œ‰ : ");
				lblEmail1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblEmail1.setBounds(80, 205, 60, 30);
				pan.add(lblEmail1);
				lblEmail2 = new JLabel();
				lblEmail2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblEmail2.setBounds(140, 205, 240, 30);
				pan.add(lblEmail2);

				lblAccess2.setText(accessList[emp.getAccess() - 1]);
				lblName2.setText(emp.getName());
				lblNo2.setText(Integer.toString(emp.getNo()));
				lblAddr2.setText(emp.getAddr());
				lblTel2.setText(emp.getTel());
				lblEmail2.setText(emp.getEmail());

				btnNot = new JButton("»∑»œ");
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
			} else if (flag == 2) {
				this.setTitle("–ﬁ∏ƒ∏ˆ»À◊ ¡œ");

				lblNo = new JLabel("π§∫≈ : ");
				lblNo.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblNo.setBounds(80, 30, 60, 30);
				pan.add(lblNo);
				txtNo = new JTextField();
				txtNo.setBounds(140, 30, 120, 30);
				pan.add(txtNo);

				lblName = new JLabel("√˚◊÷ : ");
				lblName.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblName.setBounds(80, 65, 60, 30);
				pan.add(lblName);
				txtName = new JTextField();
				txtName.setBounds(140, 65, 120, 30);
				pan.add(txtName);

				lblPassWord = new JLabel("√‹¬Î : ");
				lblPassWord.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblPassWord.setBounds(80, 100, 60, 30);
				pan.add(lblPassWord);
				txtPassWord = new JPasswordField();
				txtPassWord.setBounds(140, 100, 120, 30);
				pan.add(txtPassWord);

				lblAddr = new JLabel("µÿ÷∑ : ");
				lblAddr.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAddr.setBounds(80, 135, 60, 30);
				pan.add(lblAddr);
				txtAddr = new JTextField();
				txtAddr.setBounds(140, 135, 120, 30);
				pan.add(txtAddr);

				lblTel = new JLabel("µÁª∞ : ");
				lblTel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblTel.setBounds(80, 170, 60, 30);
				pan.add(lblTel);
				txtTel = new JTextField();
				txtTel.setBounds(140, 170, 120, 30);
				pan.add(txtTel);

				lblEmail = new JLabel("” œ‰ : ");
				lblEmail.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblEmail.setBounds(80, 205, 60, 30);
				pan.add(lblEmail);
				txtEmail = new JTextField();
				txtEmail.setBounds(140, 205, 120, 30);
				pan.add(txtEmail);

				txtName.setText(emp.getName());
				txtNo.setText(Integer.toString(emp.getNo()));
				txtPassWord.setText(emp.getPassword());
				txtAddr.setText(emp.getAddr());
				txtTel.setText(emp.getTel());
				txtEmail.setText(emp.getEmail());

				btnYes = new JButton("»∑»œ");
				btnYes.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				btnYes.setBounds(40, height - 80, 66, 30);
				btnYes.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void actionPerformed(ActionEvent e) {
						emp.setAccess(cbxAccess.getSelectedIndex() + 1);
						emp.setName(txtName.getText());
						emp.setNo(Integer.parseInt(txtNo.getText()));
						emp.setPassword(txtPassWord.getText());
						emp.setAddr(txtAddr.getText());
						emp.setTel(txtTel.getText());
						emp.setEmail(txtEmail.getText());
						new EmployeeSrv().modify(emp);
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
			} else if (flag == 3) {
				this.setTitle("πÿ”⁄◊˜’ﬂ");

				lblAccess1 = new JLabel("Author : ");
				lblAccess1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAccess1.setBounds(80, 30, 80, 30);
				pan.add(lblAccess1);
				lblAccess2 = new JLabel("1anc3r");
				lblAccess2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAccess2.setBounds(140, 30, 240, 30);
				pan.add(lblAccess2);

				lblNo1 = new JLabel("Phone : ");
				lblNo1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblNo1.setBounds(80, 65, 80, 30);
				pan.add(lblNo1);
				lblNo2 = new JLabel("15829206974");
				lblNo2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblNo2.setBounds(140, 65, 240, 30);
				pan.add(lblNo2);

				lblName1 = new JLabel("E-mail : ");
				lblName1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblName1.setBounds(80, 100, 80, 30);
				pan.add(lblName1);
				lblName2 = new JLabel("huangfangzhi@gmail.com");
				lblName2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblName2.setBounds(140, 100, 240, 30);
				pan.add(lblName2);

				lblAddr1 = new JLabel("Blog : ");
				lblAddr1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAddr1.setBounds(80, 135, 80, 30);
				pan.add(lblAddr1);
				lblAddr2 = new JLabel("www.1anc3r.me");
				lblAddr2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblAddr2.setBounds(140, 135, 240, 30);
				pan.add(lblAddr2);

				lblTel1 = new JLabel("Github : ");
				lblTel1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblTel1.setBounds(80, 170, 80, 30);
				pan.add(lblTel1);
				lblTel2 = new JLabel("1anc3r");
				lblTel2.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 16));
				lblTel2.setBounds(140, 170, 240, 30);
				pan.add(lblTel2);

				btnNot = new JButton("»∑»œ");
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
