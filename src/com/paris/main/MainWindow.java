/*
 * Join ���̶�?
 * -����ȭ�� ���� ���������� �и��� ���̺��� 
 * ��ġ �ϳ��� ���̺� ó�� ������ �� �ִ�
 * ���� ���
 *
 *inner ���� (���� ����� �Ǵ� ���̺�
 *				  �������� ���ڵ常 �����´�)
 *			�츮�� ���ݱ��� ����ؿԴ� �����̴�...
 *		������ Ư¡) �������� ���ڵ尡 �ƴ� ���
 *						������Ų��!!
 *outer ����
 *���δ���� �Ǵ� ���̺� ����� ���ڵ�
 *�Ӹ� �ƴ϶�, ������ ���̺��� ���ڵ��
 *�ϴ� ������ �� �������� ����...
 */
package com.paris.main;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import db.DBManager;
import db.SubCategory;
import db.TopCategory;

public class MainWindow extends JFrame implements ItemListener, ActionListener{
	JPanel p_west, p_center, p_east;
	JPanel p_up, p_down;
	JTable table_up, table_down;
	JScrollPane scroll_up, scroll_down;
	
	//���ʿ���
	Choice ch_top,ch_sub;
	JTextField t_name, t_price;
	Canvas can_west;
	JButton bt_regist;
	
	//���ʿ���
	Canvas can_east;
	JTextField t_id ,t_name2, t_price2;
	JButton bt_edit, bt_delete;
	
	DBManager manager;
	Connection con;
	
	//Table �� ��ü��
	UpModel upModel;
	DownModel downModel;
	
	JFileChooser chooser;
	File file;
	
	//����ī�װ�list
	ArrayList<TopCategory>topList = new ArrayList<TopCategory>();
	
	//����ī�װ�list
	ArrayList<SubCategory>subList = new ArrayList<SubCategory>();
	BufferedImage image=null;
	
	
	public MainWindow(){
		p_west=new JPanel();
		p_center=new JPanel();
		p_east=new JPanel();
		p_up=new JPanel();
		p_down=new JPanel();
		table_up = new JTable();
		table_down = new JTable();
		scroll_up=new JScrollPane(table_up);
		scroll_down=new JScrollPane(table_down);
		chooser = new JFileChooser("C:/html_workspace/images/");
		
		//���ʿ��� ������ ����
		ch_top = new Choice();
		ch_sub = new Choice();
		t_name = new JTextField(10);
		t_price = new JTextField(10);
		
		try {
			URL url = this.getClass().getResource("/default.png");
			image=ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		can_west = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage((Image)image, 0, 0, 135, 135, this);
			}
		};
		can_west.setPreferredSize(new Dimension(135, 135));
		bt_regist = new JButton("���");
		
		ch_top.setPreferredSize(new Dimension(135, 40));
		ch_sub.setPreferredSize(new Dimension(135, 40));
		ch_top.add("�� ���� ī�װ� ����");
		ch_sub.add("�� ���� ī�װ� ����");
		
		
		//���ʿ��� ������ ����
		
		
		can_east = new Canvas(){
			
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 135, 135, this);
			}
		};
		can_east.setPreferredSize(new Dimension(135, 135));
		
		t_id = new JTextField(10);
		t_id.setEditable(false);
		t_name2 = new JTextField(10);
		t_price2 = new JTextField(10);
		bt_edit = new JButton("����");
		bt_delete = new JButton("����");
		
		
		//�� �г��� ����
		p_west.setBackground(Color.green);
		p_center.setBackground(Color.YELLOW);
		p_east.setBackground(Color.CYAN);
		p_up.setBackground(Color.green);
		p_down.setBackground(Color.CYAN);
		
		//�� �гε��� ũ�� ����
		p_west.setPreferredSize(new Dimension(150, 700));
		p_center.setPreferredSize(new Dimension(550, 700));
		p_east.setPreferredSize(new Dimension(150, 700));
		
		//���ʿ����� ������Ʈ ���̱�
		p_west.add(ch_top);
		p_west.add(ch_sub);
		p_west.add(t_name);
		p_west.add(t_price);
		p_west.add(can_west);
		p_west.add(bt_regist);
		
		//���ʿ����� ������Ʈ ���̱�
		p_east.add(t_id);
		p_east.add(t_name2);
		p_east.add(t_price2);
		p_east.add(can_east);
		p_east.add(bt_edit);
		p_east.add(bt_delete);
		
		//���Ϳ� �׸��� �����ϰ� �� �Ʒ� ����
		p_center.setLayout(new GridLayout(2, 1));
		p_center.add(p_up);
		p_center.add(p_down);
		
		//��ũ�� ����
		p_up.setLayout(new BorderLayout());
		p_down.setLayout(new BorderLayout());
		p_up.add(scroll_up);
		p_down.add(scroll_down);
		
		add(p_west, BorderLayout.WEST);
		add(p_center, BorderLayout.CENTER);
		add(p_east, BorderLayout.EAST);
		
		//���̽��� ������ ����
		ch_top.addItemListener(this);
		
		//��Ϲ�ư�� ������ ����
		bt_regist.addActionListener(this);
		
		//ĵ������ ���콺 �����ʿ���
		can_west.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				preView();
			}
		});
		
		//�����̺�� �����ʿ���
		table_up.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				
				int row=table_up.getSelectedRow();
				int col=0;
				
				String subcategory_id=(String) table_up.getValueAt(row, col);
				System.out.println(subcategory_id);
				
				//������ id�� �Ʒ��� �𵨿� ��������!!
				
				downModel.getList(Integer.parseInt(subcategory_id));
				table_down.updateUI();
				
			}
		});
		table_down.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table_down.getSelectedRow();
				
				//������ ���Ϳ� ����ִ� ���͸�
				//������!! �� ���Ͱ� �ٷ� ���ڵ��
				Vector vec=downModel.data.get(row);
				
				getDetail(vec);
				
			}
		});
		
		
		setSize(850,700);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		init();
		getTop();
		getUpList(); //���� ���̺� ó��...
		getDownList();
	}
	
	//�����ͺ��̽� Ŀ�ؼ� ���
	public void init(){
		manager = DBManager.getInstance();
		con=manager.getConnection();
		
		System.out.println(con);
	}
	
	//�ֻ��� ī�װ� ���
	public void getTop(){
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		String sql="select * from top_category order by top_category_id asc";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				TopCategory dto = new TopCategory();
				dto.setTop_category_id(rs.getInt("top_category_id"));
				dto.setTop_name(rs.getString("top_name"));
				
				topList.add(dto);
				ch_top.add(dto.getTop_name());
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//���� ���̺� ������ ó��
	public void getUpList(){
		table_up.setModel(upModel = new UpModel(con));
		table_up.updateUI();
		
		
	}
	
	public void getDownList(){
		table_down.setModel(downModel = new DownModel(con));
		
		
	}
	
	//���� ī�װ� ���ϱ�
	//���ε� ����
	public void getSub(){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from subcategory where top_category_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			//���ε� ������ ����
			int index=ch_top.getSelectedIndex();
			if(index-1>=0){
				TopCategory dto=topList.get(index-1);
				pstmt.setInt(1,dto.getTop_category_id());//ù��° �߰ߵ� ���ε� ����...
				rs = pstmt.executeQuery();
				
				//��� ���� �����
				subList.removeAll(subList);
				ch_sub.removeAll();//���̽� �����!!
				
				//����ī�װ� ä���!!
				while(rs.next()){
					SubCategory vo = new SubCategory();
					
					vo.setTop_category_id(rs.getInt("top_category_id"));
					vo.setSubcategory_id(rs.getInt("subcategory_id"));
					vo.setSub_name(rs.getString("sub_name"));
					
					subList.add(vo);
					ch_sub.add(vo.getSub_name());
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void itemStateChanged(ItemEvent e) {
		getSub();
	}
	
	/*---------------------------------------------
	 ��ǰ���
	 -----------------------------------------------*/
	public void regist(){
		PreparedStatement pstmt=null;
		
		String sql="insert into product(product_id, subcategory_id, product_name, price, img)";
		sql+=" values(seq_product.nextval,?,?,?,?)";
		
		System.out.println(sql);
		try {
			pstmt=con.prepareStatement(sql);
			
			//ArrayList �ȿ� ����ִ�
			//SubCategory DTO�� �����Ͽ�
			//PK ���� �־�����!!
			int index=ch_sub.getSelectedIndex();
			SubCategory vo=subList.get(index);
			
			//���ε� ������ �� �� ����
			pstmt.setInt(1,vo.getSubcategory_id());
			pstmt.setString(2, t_name.getText());
			pstmt.setInt(3, Integer.parseInt(t_price.getText()));
			pstmt.setString(4,file.getName());
			
			//executeUpdate �޼���� ������ ���� �� �ݿ���
			//���ڵ��� ������ ��ȯ���ش� ���� insert���� ���
			//������ �����޴ٸ� 1��, update 1���̻�, delete 1��
			//���)insert�� ��ȯ���� 0�̶�� insert ����!!
			int result = pstmt.executeUpdate();
			if(result!=0){
				JOptionPane.showMessageDialog(this, "��ϼ���");
				//DB�� ���Ӱ� ������ ������ ���� ����...
				upModel.getList();
				table_up.updateUI();
				//�̹��� ���Ϻ���
				copy();
			}else{
				JOptionPane.showMessageDialog(this, "��Ͻ���");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//ĵ������ �̹��� �ݿ��ϱ�
	public void preView(){
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			//ĵ������ �̹��� �׸���!!
			file=chooser.getSelectedFile();
			//����� ������ ������ �̹����� ��ü�Ͽ� �ٽ� �׸���
			try {
				image = ImageIO.read(file);
				can_west.repaint();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
	}
	
	//����޼��� ����
	public void copy(){
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream("C:/java_workspace2/BreadProject/data/"+file.getName());
			
			byte[] b= new byte[1024];
			
			int flag; //-1���� ���� �Ǵ�
			while(true){
				flag=fis.read(b);
				if(flag==-1)break;
				fos.write(b);
			}
			System.out.println("�̹��� ����Ϸ�!!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//������ ��ǰ�� ������ �����ֱ�
	public void getDetail(Vector vec){
		t_id.setText(vec.get(0).toString());
		t_name2.setText(vec.get(2).toString());
		t_price2.setText(vec.get(3).toString());
		
		try {
			image = ImageIO.read(new File("C:/java_workspace2/BreadProject/data/"+file.getName()));
			can_east.repaint();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		regist();
	}
	

	public static void main(String[] args) {
		new MainWindow();
	}





}
