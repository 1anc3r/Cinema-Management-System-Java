package me.lancer.cms.view;

import java.awt.BorderLayout;
import java.awt.Component;
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
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import me.lancer.cms.dao.DataDictDAO;
import me.lancer.cms.model.DataDict;
import me.lancer.cms.service.DataDictSrv;
import me.lancer.cms.service.EmployeeSrv;

class DataDictTreeCellRenderer extends DefaultTreeCellRenderer {

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

class DataDictTableMouseListener extends MouseAdapter {

	private JTable table;
	private DataDict ddict;

	public DataDict getDict() {
		return ddict;
	}

	public DataDictTableMouseListener(JTable table, Object[] number, DataDict ddict) {
		this.ddict = ddict;
		this.table = table;
	}

	public void mouseClicked(MouseEvent event) {
		int row = table.getSelectedRow();
		ddict.setId(Integer.parseInt(table.getValueAt(row, 0).toString()));
		ddict.setSuperId(Integer.parseInt(table.getValueAt(row, 1).toString()));
		ddict.setIndex(Integer.parseInt(table.getValueAt(row, 2).toString()));
		ddict.setName(table.getValueAt(row, 3).toString());
		if (table.getValueAt(row, 4) != null) {
			ddict.setValue(table.getValueAt(row, 4).toString());
		} else {
			ddict.setValue("");
		}
	}
}

class DataDictTable {

	private DataDict ddict;

	public DataDictTable(DataDict ddict) {
		this.ddict = ddict;
	}

	@SuppressWarnings("serial")
	public void createTable(JTable dictTable, JScrollPane scrollPane, Object[] columnNames, List<DataDict> dictList) {
		try {
			Object data[][] = new Object[dictList.size()][columnNames.length];
			Iterator<DataDict> itr = dictList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				DataDict ddict = itr.next();
				data[i][0] = Integer.toString(ddict.getId());
				data[i][1] = Integer.toString(ddict.getSuperId());
				data[i][2] = Integer.toString(ddict.getIndex());
				data[i][3] = ddict.getName();
				data[i][4] = ddict.getValue();
				i++;
			}
			new JTable(data, columnNames);
			javax.swing.table.DefaultTableModel t = new javax.swing.table.DefaultTableModel(data, columnNames) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			t.setDataVector(data, columnNames);
			DefaultTableModel model = (DefaultTableModel) (dictTable.getModel());
			model.setDataVector(data, columnNames);
			dictTable.repaint();
			dictTable.setRowHeight(24);
			dictTable.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 16));
			dictTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(JLabel.CENTER);
			dictTable.setDefaultRenderer(Object.class, r);
			dictTable.setBounds(0, 0, 700, 450);
			DataDictTableMouseListener tml = new DataDictTableMouseListener(dictTable, columnNames, ddict);
			dictTable.addMouseListener(tml);
			dictTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			scrollPane.add(dictTable);
			scrollPane.setViewportView(dictTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showTable(JTable dictTable, JScrollPane scrollPane, Object[] columnNames, List<DataDict> dictList) {
		try {
			Object data[][] = new Object[dictList.size()][columnNames.length];
			Iterator<DataDict> itr = dictList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				DataDict ddict = itr.next();
				data[i][0] = Integer.toString(ddict.getId());
				data[i][1] = Integer.toString(ddict.getSuperId());
				data[i][2] = Integer.toString(ddict.getIndex());
				data[i][3] = ddict.getName();
				data[i][4] = ddict.getValue();
				i++;
			}
			DefaultTableModel model = (DefaultTableModel) (dictTable.getModel());
			model.setDataVector(data, columnNames);
			dictTable.repaint();
			scrollPane.setViewportView(dictTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class DataDictUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private DataDict ddict = new DataDict();
	JSplitPane splitPane = new JSplitPane();
	private JScrollPane scrollPane;
	private JPanel btnList;
	private JButton btnAdd, btnEdit, btnDel, btnQuery;
	private JTable dictTable = null;
	List<DataDict> rst = new ArrayList<>();

	public DataDictUI() {
		super(new BorderLayout());
		initContent();
	}

	protected void initContent() {

		splitPane = new JSplitPane();
		JPanel right = new JPanel();
		JPanel left = new JPanel();
		dictTable = new JTable();
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
				List<DataDict> leafList = null;
				if (node == null) {
					return;
				}
				Object object = node.getUserObject();
				if (node.isRoot()) {
					new DataDictSrv().findAllSonByID(leafList, 1);
					showAllLeafTable(leafList);
				} else {
				}
				DataDict dict = (DataDict) object;
				leafList = new ArrayList<DataDict>();
				new DataDictSrv().findAllSonByID(leafList, dict.getId());
				showAllLeafTable(leafList);

			}
		});

		btnList = new JPanel();

		btnAdd = new JButton("添加");
		btnAdd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataDictDialog ddictDialog = new DataDictDialog(1);// 弹出对话框
				ddictDialog.toFront();
				ddictDialog.setModal(true);
				ddictDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnAdd);

		btnQuery = new JButton("查找");
		btnQuery.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataDictDialog ddictDialog = new DataDictDialog(2);// 弹出对话框
				ddictDialog.toFront();
				ddictDialog.setModal(true);
				ddictDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnQuery);

		btnEdit = new JButton("修改");
		btnEdit.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataDictDialog ddictDialog = new DataDictDialog(3);// 弹出对话框
				ddictDialog.toFront();
				ddictDialog.setModal(true);
				ddictDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataDictDialog ddictDialog = new DataDictDialog(4);// 弹出对话框
				ddictDialog.toFront();
				ddictDialog.setModal(true);
				ddictDialog.setVisible(true);
				showTable();
			}
		});
		btnList.add(btnDel);

		right.add(btnList, BorderLayout.SOUTH);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(8000, 6000));
		right.add(scrollPane, BorderLayout.NORTH);
		splitPane.setBounds(0, 0, 800, 600);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(left);
		splitPane.setRightComponent(right);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(200);
		this.add(splitPane);

		Object[] in = { "id", "superid", "index", "name", "value" };
		DataDictTable tms = new DataDictTable(ddict);
		List<DataDict> leafList = new ArrayList<DataDict>();
		new DataDictSrv().findAllSonByID(leafList, 1);
		showAllLeafTable(leafList);
		tms.createTable(dictTable, scrollPane, in, leafList);
		scrollPane.repaint();
		splitPane.repaint();
	}

	private JTree createTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("数据字典 ");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		JTree tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		addTreeNode(root, 1);
		tree.expandRow(0);
		tree.setSelectionRow(0);
		return tree;
	}

	private void addTreeNode(DefaultMutableTreeNode treeNode, int superID) {
		DataDictDAO dictDAO = new DataDictDAO();
		List<DataDict> list = dictDAO.findByID(superID);
		if (list.size() > 0) {
			DefaultMutableTreeNode node = null;
			for (int i = 0; i < list.size(); i++) {
				if (dictDAO.hasChildren(list.get(i).getId())) {
					node = new DefaultMutableTreeNode(list.get(i));
					addTreeNode(node, list.get(i).getId());
					treeNode.add(node);
				}
			}
		}
	}

	public void showAllLeafTable(List<DataDict> leafList) {
		DataDictTable tms = new DataDictTable(ddict);
		Object[] in = { "ID", "父节点", "坐标", "名字", "值" };
		if (rst.size() > 0) {
			leafList = rst;
		}
		tms.showTable(dictTable, scrollPane, in, leafList);
		scrollPane.repaint();
		splitPane.repaint();
	}

	public void showTable() {
		DataDictTable tms = new DataDictTable(ddict);
		Object[] in = { "ID", "父节点", "坐标", "名字", "值" };
		List<DataDict> dictList = new LinkedList<DataDict>();
		new DataDictSrv().findAllSonByID(dictList, 1);
		if (rst.size() > 0) {
			dictList = rst;
		}
		tms.showTable(dictTable, scrollPane, in, dictList);
		scrollPane.repaint();
		splitPane.repaint();
	}

	public static void showPanel() {
		JFrame frame = new JFrame("数据字典管理");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new DataDictUI());
		frame.pack();
		frame.setVisible(true);
	}

	class DataDictDialog extends JDialog {

		private static final long serialVersionUID = 1L;
		final int flag;
		private int frmWidth = 400;
		private int frmHeight = 300;
		private JPanel pan = new JPanel();
		private JLabel lblParent, lblName, lblValue;
		private JTextField txtParent, txtName, txtValue;
		private JButton btnYes, btnNot;

		DataDictDialog(final int flag) {
			this.flag = flag;
			System.out.println("" + flag);
			this.setTitle("数据字典操作");
			this.setSize(frmWidth, frmHeight);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});

			lblParent = new JLabel("父节点 : ");
			lblParent.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblParent.setBounds(80, 30, 60, 30);
			pan.add(lblParent);
			txtParent = new JTextField();
			txtParent.setBounds(140, 30, 120, 30);
			pan.add(txtParent);

			lblName = new JLabel("名字 : ");
			lblName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblName.setBounds(80, 65, 60, 30);
			pan.add(lblName);
			txtName = new JTextField();
			txtName.setBounds(140, 65, 120, 30);
			pan.add(txtName);

			lblValue = new JLabel("值 : ");
			lblValue.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			lblValue.setBounds(80, 100, 60, 30);
			pan.add(lblValue);
			txtValue = new JTextField();
			txtValue.setBounds(140, 100, 120, 30);
			pan.add(txtValue);

			if (flag == 3 || flag == 4) {
				txtName.setText(ddict.getName());
				txtParent.setText(Integer.toString(ddict.getSuperId()));
				txtValue.setText("");
			}

			btnYes = new JButton("确认");
			btnYes.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnYes.setBounds(40, frmHeight - 80, 66, 30);
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

			btnNot = new JButton("取消");
			btnNot.setFont(new Font("微软雅黑", Font.PLAIN, 16));
			btnNot.setBounds(frmWidth - 106, frmHeight - 80, 66, 30);
			btnNot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}

			});
			pan.add(btnNot);

			pan.setBounds(0, 0, frmWidth, this.frmHeight);
			pan.setLayout(null);
			this.add(pan);
		}

		private void btnAddClicked() {
			if (txtParent.getText().length() > 0 && txtName.getText().length() > 0 && txtValue.getText().length() > 0) {
				DataDict ddict = new DataDict();
				ddict.setSuperId(Integer.parseInt(txtParent.getText()));
				ddict.setName(txtName.getText());
				ddict.setValue(txtValue.getText());
				List<DataDict> ddList = new DataDictSrv()
						.Fetch("dict_parent_id=" + Integer.parseInt(txtParent.getText()));
				ddict.setIndex(ddList.get(ddList.size() - 1).getIndex() + 1);
				new DataDictSrv().add(ddict);
			} else {
				JOptionPane.showMessageDialog(null, "数据不完整");
			}
		}

		private void btnQueryClicked() {
			DataDict datadict = new DataDict();
			String sql = "";
			if (txtParent.getText().length() > 0) {
				datadict.setSuperId(Integer.parseInt(txtParent.getText()));
				if (sql.equals("")) {
					sql += " dict_parent_id=" + txtParent.getText();
				} else {
					sql += " and dict_parent_id=" + txtParent.getText();
				}
			}
			if (txtName.getText().length() > 0) {
				datadict.setName(txtName.getText());
				if (sql.equals("")) {
					sql += " dict_name='" + txtName.getText() + "'";
				} else {
					sql += " and dict_name='" + txtName.getText() + "'";
				}
			}
			if (txtValue.getText().length() > 0) {
				datadict.setValue(txtValue.getText());
				if (sql.equals("")) {
					sql += " dict_value='" + txtValue.getText() + "'";
				} else {
					sql += " and dict_value='" + txtValue.getText() + "'";
				}
			}
			System.out.println(sql);
			rst = new DataDictSrv().Fetch(sql);
		}

		private void btnModClicked() {
			ddict.setSuperId(Integer.parseInt(txtParent.getText()));
			ddict.setName(txtName.getText());
			ddict.setValue(txtValue.getText());
			new DataDictSrv().modify(ddict);
		}

		private void btnDelClicked() {
			int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				EmployeeSrv stuSrv = new EmployeeSrv();
				stuSrv.delete(ddict.getId());
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
