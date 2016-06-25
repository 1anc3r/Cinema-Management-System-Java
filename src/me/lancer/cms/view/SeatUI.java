package me.lancer.cms.view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import me.lancer.cms.model.Play;
import me.lancer.cms.model.Sale;
import me.lancer.cms.model.SaleItem;
import me.lancer.cms.model.Schedule;
import me.lancer.cms.model.Seat;
import me.lancer.cms.model.Studio;
import me.lancer.cms.model.Ticket;
import me.lancer.cms.service.EmployeeSrv;
import me.lancer.cms.service.PlaySrv;
import me.lancer.cms.service.SaleItemSrv;
import me.lancer.cms.service.SaleSrv;
import me.lancer.cms.service.ScheduleSrv;
import me.lancer.cms.service.SeatSrv;
import me.lancer.cms.service.SellTicketHandler;
import me.lancer.cms.service.StudioSrv;
import me.lancer.cms.service.TicketSrv;

class PlayTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setText(value.toString());
		if (sel) {
			setForeground(getTextSelectionColor());
		} else {
			setForeground(getTextNonSelectionColor());
		}
		this.setIcon(new ImageIcon("resource/image/dict.gif"));
		return this;
	}
}

public class SeatUI extends JPanel {

	private static final long serialVersionUID = 1L;
	float prices = 0;
	int empId;
	Schedule sched = null;
	Seat seat = null;
	Sale sale = null;
	Ticket ticket = null;
	SellTicketHandler sellTicket = new SellTicketHandler();
	JButton btnSeats[][] = null;
	Map<Integer, Integer> mapTab = new HashMap<Integer, Integer>();
	Map<Integer, JButton[][]> mapScs = new HashMap<Integer, JButton[][]>();
	int tabCount = 0;
	int seatCount = 0;
	int rst = 0;
	List<Integer> rstList = new ArrayList<>();
	ImageIcon iconSold = new ImageIcon("image/seat_sold.png");
	ImageIcon iconSelected = new ImageIcon("image/seat_selected.png");
	ImageIcon iconSale = new ImageIcon("image/seat_sale.png");
	ImageIcon iconBroken = new ImageIcon("image/seat_broken.png");
	ImageIcon iconNull = new ImageIcon("");

	JSplitPane splitPane = new JSplitPane();
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane;
	private JPanel seatPanel;

	public SeatUI(int empId) {
		super(new BorderLayout());
		this.empId = empId;
		sellTicket.makeNewSale();
		initContent();
	}

	protected void initContent() {
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				sellTicket.removeAllTicket();
			}
		});

		iconSold.setImage(iconSold.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		iconSelected.setImage(iconSelected.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		iconSale.setImage(iconSale.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		iconBroken.setImage(iconBroken.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		iconNull.setImage(iconNull.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

		splitPane = new JSplitPane();
		JPanel right = new JPanel();
		JPanel left = new JPanel();
		right.setLayout(new BorderLayout());
		right.setBounds(200, 0, 600, 600);
		left.setLayout(null);
		left.setBounds(0, 0, 200, 600);
		final JTree tree = createTree();
		tree.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		DataDictTreeCellRenderer render = new DataDictTreeCellRenderer();
		tree.setCellRenderer(render);
		left.setLayout(new BorderLayout());
		left.add(tree);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (node == null) {
					return;
				}
				Object object = node.getUserObject();
				if (node.isRoot()) {
				} else if (node.isLeaf()) {
					sched = (Schedule) object;
					int key = 0;
					for (Map.Entry<Integer, Integer> entryTab : mapTab.entrySet()) {
						if (entryTab.getValue() == sched.getStudioId()) {
							key = entryTab.getKey();
							for (Map.Entry<Integer, JButton[][]> entryScs : mapScs.entrySet()) {
								if (entryScs.getKey() == entryTab.getKey()) {
									btnSeats = entryScs.getValue();
								}
							}
						}
					}
					showSeatsTable();
					tabbedPane.setSelectedIndex(key - 1);
					tabbedPane.repaint();
				} else {
				}
			}
		});
		right.add(createtabbedPane());

		splitPane.setBounds(0, 0, 8000, 6000);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(left);
		splitPane.setRightComponent(right);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(200);
		this.add(splitPane);
	}

	private JTabbedPane createtabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		StudioSrv studSrv = new StudioSrv();
		List<Studio> studList = studSrv.FetchAll();
		tabCount = 0;
		for (Studio studItem : studList) {
			int row = studItem.getRowCount();
			int col = studItem.getColCount();
			SeatSrv seatSrv = new SeatSrv();
			List<Seat> seatList = seatSrv.Fetch("studio_id=" + studItem.getID());
			seatPanel = new JPanel();
			seatPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			seatPanel.setLayout(new GridLayout(row, col, 0, 0));
			btnSeats = new JButton[row][col];
			for (int i = 1; i <= row; i++) {
				for (int j = 1; j <= col; j++) {
					seat = new Seat();
					seat.setSeatStatus(-1);
					for (Seat seatItem : seatList) {
						if (seatItem.getRow() == i && seatItem.getColumn() == j) {
							seat = seatItem;
							break;
						}
					}
					final JButton btnSeat = new JButton();
					btnSeat.setBackground(Color.WHITE);
					btnSeat.setPreferredSize(new Dimension(30, 30));
					btnSeat.setOpaque(false);
					ImageIcon icon = null;
					btnSeat.setVisible(true);
					if (seat.getSeatStatus() == 0) {
						icon = new ImageIcon("");
						icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
					} else if (seat.getSeatStatus() == -1) {
						icon = new ImageIcon("image/seat_broken.png");
						icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
					} else if (seat.getSeatStatus() == 1) {
						icon = new ImageIcon("image/seat_sale.png");
						icon.setImage(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
					}
					btnSeat.setIcon(icon);
					btnSeat.setBorderPainted(false);
					btnSeat.setName("" + seat.getId());
					btnSeat.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							SeatDialog seatDialog = new SeatDialog(
									Integer.parseInt(((JButton) e.getSource()).getName()));
							seatDialog.toFront();
							seatDialog.setModal(true);
							seatDialog.setVisible(true);
							if (rst != -1) {
								if (rst < 0) {
									rst = -rst;
									if (new SeatSrv().Fetch("seat_id=" + rst).get(0).getSeatStatus() == -1) {
										((JButton) e.getSource()).setIcon(iconBroken);
									} else if (new SeatSrv().Fetch("seat_id=" + rst).get(0).getSeatStatus() == 0) {
										((JButton) e.getSource()).setIcon(iconNull);
									} else if (new SeatSrv().Fetch("seat_id=" + rst).get(0).getSeatStatus() == 1) {
										((JButton) e.getSource()).setIcon(iconSale);
									}
								} else if (rst > 0) {
									if (new TicketSrv().Fetch("seat_id=" + rst + " and " + "sched_id=" + sched.getId())
											.get(0).getStatus() == 0) {
										((JButton) e.getSource()).setIcon(iconSale);
									} else if (new TicketSrv()
											.Fetch("seat_id=" + rst + " and " + "sched_id=" + sched.getId()).get(0)
											.getStatus() == 1) {
										((JButton) e.getSource()).setIcon(iconSelected);
									} else if (new TicketSrv()
											.Fetch("seat_id=" + rst + " and " + "sched_id=" + sched.getId()).get(0)
											.getStatus() == 9) {
										((JButton) e.getSource()).setIcon(iconSold);
									}
								}
								showSeatsTable();
								rst = 0;
							}
						}

					});
					btnSeats[i - 1][j - 1] = btnSeat;
					seatPanel.add(btnSeats[i - 1][j - 1]);
				}
			}

			tabCount++;
			mapTab.put(tabCount, studItem.getID());
			mapScs.put(tabCount, btnSeats);

			scrollPane = new JScrollPane(seatPanel);
			scrollPane.setPreferredSize(new Dimension(8000, 6000));
			tabbedPane.addTab(studItem.getName(), scrollPane);
		}
		tabbedPane.setPreferredSize(new Dimension(540, 540));
		return tabbedPane;
	}

	private JTree createTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("所有剧目");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		JTree tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		addTreeNode(root);
		tree.expandRow(0);
		tree.setSelectionRow(0);
		return tree;
	}

	private void addTreeNode(DefaultMutableTreeNode root) {
		PlaySrv playSrv = new PlaySrv();
		List<Play> playList = playSrv.FetchAll();
		DefaultMutableTreeNode parentNode = null;

		for (Play playItem : playList) {
			parentNode = new DefaultMutableTreeNode(playItem.getName());
			root.add(parentNode);
			ScheduleSrv schedSrv = new ScheduleSrv();
			List<Schedule> schedList = schedSrv.FetchAll();
			DefaultMutableTreeNode childNode = null;
			for (Schedule schedItem : schedList) {
				if (schedItem.getPlayId() == playItem.getId()) {
					childNode = new DefaultMutableTreeNode(schedItem);
					parentNode.add(childNode);
				}
			}
		}
	}

	public void showSeatsTable() {
		if (sched != null) {
			for (Ticket item : new TicketSrv().Fetch("sched_id=" + sched.getId())) {
				int i = new SeatSrv().Fetch("seat_id=" + item.getSeatId()).get(0).getRow();
				int j = new SeatSrv().Fetch("seat_id=" + item.getSeatId()).get(0).getColumn();
				if (item.getSeatId() == Integer.parseInt(btnSeats[i - 1][j - 1].getName())) {
					if (item.getStatus() == 9) {
						btnSeats[i - 1][j - 1].setIcon(iconSold);
					} else if (item.getStatus() == 1) {
						btnSeats[i - 1][j - 1].setIcon(iconSelected);
					} else if (item.getStatus() == 0) {
						btnSeats[i - 1][j - 1].setIcon(iconSale);
					}
				}
			}
		}
		tabbedPane.repaint();
	}

	public static void showPanel() {
		JFrame frame = new JFrame("座位管理");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SeatUI(1));
		frame.pack();
		frame.setVisible(true);
	}

	class SeatDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		Seat sseat;
		private int width = 400;
		private int height = 300;
		private JPanel pan = new JPanel();
		private JLabel lblEdit, lblTxt1, lblTxt2, lblPayment, lblPayment1, lblPayment2;
		private JTextField txtPayment;
		private JComboBox<String> cbxFlag;
		private JButton btnChoose, btnEdit, btnBuy, btnBye;
		String flagList[] = { "损坏", "空位", "完好" };

		SeatDialog(int seatId) {
			sseat = new SeatSrv().Fetch("seat_id=" + seatId).get(0);
			this.setTitle("座位操作");
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					rst = -1;
					dispose();
				}
			});

			lblTxt1 = new JLabel("当前 : ");
			lblTxt1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblTxt1.setBounds(80, 30, 60, 30);
			pan.add(lblTxt1);

			lblTxt2 = new JLabel("" + flagList[seat.getSeatStatus() + 1]);
			lblTxt2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblTxt2.setBounds(140, 30, 120, 30);
			pan.add(lblTxt2);

			lblEdit = new JLabel("修改 : ");
			lblEdit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblEdit.setBounds(80, 65, 60, 30);
			pan.add(lblEdit);

			cbxFlag = new JComboBox<String>(flagList);
			cbxFlag.setBounds(140, 65, 120, 30);
			pan.add(cbxFlag);

			lblPayment1 = new JLabel("应付 : ");
			lblPayment1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblPayment1.setBounds(80, 100, 60, 30);
			pan.add(lblPayment1);
			lblPayment2 = new JLabel(prices + "元");
			lblPayment2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblPayment2.setBounds(140, 100, 60, 30);
			pan.add(lblPayment2);

			lblPayment = new JLabel("已付 : ");
			lblPayment.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblPayment.setBounds(80, 135, 60, 30);
			pan.add(lblPayment);
			txtPayment = new JTextField();
			txtPayment.setBounds(140, 135, 60, 30);
			pan.add(txtPayment);

			btnChoose = new JButton("选座");
			btnChoose.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnChoose.setBounds(40, height - 80, 66, 30);
			btnChoose.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnChooseClicked(sseat);
					dispose();
				}

			});

			pan.add(btnChoose);
			btnBuy = new JButton("购票");
			btnBuy.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnBuy.setBounds(130, height - 80, 66, 30);
			btnBuy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnBuyClicked();
					dispose();
				}

			});
			pan.add(btnBuy);

			btnBye = new JButton("退票");
			btnBye.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnBye.setBounds(220, height - 80, 66, 30);
			btnBye.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnByeClicked(sseat);
					dispose();
				}

			});
			pan.add(btnBye);

			btnEdit = new JButton("修改");
			btnEdit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnEdit.setBounds(310, height - 80, 66, 30);
			btnEdit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (new EmployeeSrv().Fetch("emp_id=" + empId).get(0).getAccess() != 1) {
						btnEditClicked(sseat, cbxFlag.getSelectedIndex() - 1);
					} else {
						JOptionPane.showMessageDialog(null, "没有权限!");
					}
					dispose();
				}

			});
			pan.add(btnEdit);

			pan.setBounds(0, 0, width, this.height);
			pan.setLayout(null);
			this.add(pan);
		}

		private void btnChooseClicked(Seat bseat) {
			if (sched != null) {
				if (bseat.getSeatStatus() == 1) {
					if (new TicketSrv().Fetch("seat_id=" + bseat.getId()).size() == 0
							|| new TicketSrv().Fetch("seat_id=" + bseat.getId()).get(0).getStatus() == 0) {
						ticket = sellTicket.makeNewTicket(sched, bseat);
						sellTicket.addTicket(ticket);
						prices += ticket.getPrice();
						rst = bseat.getId();
						rstList.add(bseat.getId());
					} else if (new TicketSrv().Fetch("seat_id=" + bseat.getId()).size() != 0
							&& new TicketSrv().Fetch("seat_id=" + bseat.getId()).get(0).getStatus() == 1) {
						ticket = new TicketSrv().Fetch("seat_id=" + bseat.getId()).get(0);
						ticket.setStatus(0);
						prices -= ticket.getPrice();
						new TicketSrv().modify(ticket);
						rst = bseat.getId();
						rstList.remove(bseat.getId());
					} else if (new TicketSrv().Fetch("seat_id=" + bseat.getId()).size() != 0
							&& new TicketSrv().Fetch("seat_id=" + bseat.getId()).get(0).getStatus() == 9) {
						JOptionPane.showMessageDialog(null, "该座位已售!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "该座位已坏!");
					rst = -1;
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先选择场次!");
				rst = -1;
			}
		}

		private void btnEditClicked(Seat bseat, int status) {
			if (status == -1 || status == 0 || status == 1) {
				bseat.setSeatStatus(status);
				new SeatSrv().modify(bseat);
				rst = -bseat.getId();
			} else {
				JOptionPane.showMessageDialog(null, "无效的状态!");
				rst = -1;
			}
		}

		private void btnBuyClicked() {
			if (sched != null && txtPayment.getText().length() > 0) {
				sale = new Sale();
				sale.setEmpId(empId);
				sale.setPayment(Float.parseFloat(txtPayment.getText()));
				System.out.println(sellTicket.getInfo());
				JOptionPane.showMessageDialog(null, sellTicket.getInfo());
				sellTicket.doSale(sale);
				rst = 0;
			} else {
				JOptionPane.showMessageDialog(null, "请先输入付款");
				rst = -1;
			}
		}

		private void btnByeClicked(Seat bseat) {
			if (sched != null) {
				if (bseat.getSeatStatus() == 1) {
					ticket = new TicketSrv().Fetch("seat_id=" + bseat.getId() + " and " + "sched_id=" + sched.getId())
							.get(0);
					sale = new SaleSrv().Fetch(
							"sale_id=" + new SaleItemSrv().Fetch("ticket_id=" + ticket.getId()).get(0).getSaleId())
							.get(0);
					sale.setTime(new Timestamp(new Date().getTime()));
					sale.setStatus(-1);
					new SaleSrv().modify(sale);
					for (SaleItem item : new SaleItemSrv().Fetch("sale_id=" + sale.getId())) {
						Ticket tick = new TicketSrv().Fetch("ticket_id=" + item.getTicketId()).get(0);
						tick.setStatus(0);
						new TicketSrv().modify(tick);
					}
					rst = bseat.getId();
				} else {
					JOptionPane.showMessageDialog(null, "该座位已坏/已选/已售!");
					rst = -1;
				}
			} else {
				JOptionPane.showMessageDialog(null, "请先选择一部电影以及场次");
				rst = -1;
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
