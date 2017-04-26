//package com.xiao.hongleaf;
//import java.awt.BorderLayout;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.event.ActionListener;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFrame;
//
//import java.awt.event.ActionEvent;
//import java.awt.image.BufferedImage;
//import java.io.*;
//
//import javax.swing.*;
//
//import mseMove.DragDao;
//import ConvertImg_Array.imgToArray;
//import c_ImgProcess.c_OtsuYuZhi;
//import c_ImgProcess.c_GetArea;
//public class c_From extends JFrame implements ActionListener
//{
//	public DragDao drag = new DragDao();
//	private JLabel lbl_1 = null; // ��һ���������ʾ��
//	private JTextField txt_Numb1 = null;// �ļ����ڵ�ַ
//	private JButton btn_Openfile = null; // ȷ����ť
//	private JButton btn_CutFig = null; // ȷ����ǵ�
//	private JButton btn_SAThings = null; // ȷ���Ǳ���
//	private JButton btn_CalculateArea = null; // �������
//	int [][] img=null;//����ͼ������
//	int [][] img_TiQu=null;//��ȡ����������(ѡ���ڵ�)
//	JLabel labelPic = new JLabel(); //�ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ����������
//	int iLeaf=0,irect=0;
//	public int Lx,Ly,Rx,Ry;
//	imgToArray im2Ar=new imgToArray();//ͼƬ���ά�����໥ת��
//	ImageIcon bg=null;
//	int int_Shink;
//
//	//
//
//	private void Init_txtFile()
//	{
//		txt_Numb1 = new JTextField(10);
//		txt_Numb1.enable(true);
//	}
//	// �Ա�ǩ���г�ʼ��
//	private void Init_Lbl()
//	{
//		lbl_1 = new JLabel("�ļ�����λ��:");
//	}
//
//	// �԰�ť���г�ʼ��
//	private void Init_Btn()
//	{
//		btn_Openfile = new JButton("���ļ�");// ȷ����ť��ʼ��
//		btn_CutFig=new JButton("ȷ����ǵ�");// ȷ����ǵ�
//		btn_SAThings=new JButton("ȷ��������");// ȷ��������
//		btn_CalculateArea=new JButton("�������");// �������
//	}
//
//	// �԰�ť�󶨼���
//	private void BindListener()
//	{
//		btn_Openfile.addActionListener(this);// Ϊ<ȷ��>��ť�󶨼�������
//		btn_CutFig.addActionListener(this);// Ϊ<ȷ��>��ť�󶨼�������
//		btn_SAThings.addActionListener(this);// Ϊ<ȷ��>��ť�󶨼�������
//		btn_CalculateArea.addActionListener(this);// Ϊ<ȷ��>��ť�󶨼�������
//	}
//
//	// ���ÿؼ�λ��
//	private void SetControlPosition() {
//		lbl_1.setBounds(10, 20, 130, 30);// ��ǩ1 λ���趨
//		txt_Numb1.setBounds(95, 22, 230, 30);// �����1 λ���趨
//		btn_Openfile.setBounds(350, 22, 100, 30);//
//		drag.setLocation(5, 55);
//		btn_CutFig.setBounds(900, 22, 150, 30);
//		btn_SAThings.setBounds(1060, 22, 150, 30);
//		btn_CalculateArea.setBounds(1220, 22, 150, 30);
//	}
//
//	// ���ؼ����������
//	private void AddCtrl()
//	{
//		this.add(lbl_1);
//		this.add(txt_Numb1);
//		this.add(btn_Openfile);
//		this.add(btn_CutFig);
//		this.add(btn_SAThings);
//		this.add(btn_CalculateArea);
//		drag.setOpaque(false);//��͸��
//		this.add(drag,BorderLayout.CENTER);
//	}
//	public c_From()
//	{
//		this.setLayout(null);
//		Init_Lbl();// ���ñ�ǩ��ʼ������
//		Init_txtFile();// ����������ʼ������
//		Init_Btn();// ���ð�ť��ʼ������
//		BindListener();// �󶨼���
//		SetControlPosition();// �趨�ؼ�λ��
//		btn_CutFig.setEnabled(false);
//		btn_SAThings.setEnabled(false);
//		btn_CalculateArea.setEnabled(false);
//		AddCtrl();// ���ؼ����������
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setSize(1440, 900);
//		this.setLocationRelativeTo(null);
//		this.setTitle("ͼ���������");
//		this.setVisible(true);
//	}
//
//	public static void main(String[] args)
//	{
//		new c_From();
//	}
//	/**
//     * ��ͼƬ������С
//     * @param originalImage ԭʼͼƬ
//     * @param times ��С����
//     * @return ��С���Image
//     */
//    public static BufferedImage  zoomOutImage(BufferedImage  originalImage, Integer times)
//    {
//        int width = originalImage.getWidth()/times;
//        int height = originalImage.getHeight()/times;
//        BufferedImage newImage = new BufferedImage(width,height,originalImage.getType());
//        Graphics g = newImage.getGraphics();
//        g.drawImage(originalImage, 0,0,width,height,null);
//        g.dispose();
//        return newImage;
//    }
//	@Override
//	public void actionPerformed(ActionEvent arg0)
//	{
//		if (arg0.getSource() == btn_Openfile)
//		{
//			this.repaint();//ҳ��ˢ��
//			JFileChooser jfc = new JFileChooser();
//			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//			jfc.showDialog(new JLabel(), "ѡ��");
//			File file = jfc.getSelectedFile();
//			ImageIcon bg=null;
//			bg = new ImageIcon(file.getAbsolutePath());
//			int_Shink=4;//��С����
//			bg.setImage(bg.getImage().getScaledInstance(bg.getIconWidth()/int_Shink,bg.getIconHeight()/int_Shink,Image.SCALE_DEFAULT));
//
//			labelPic.removeAll();
//			this.repaint();//ҳ��ˢ��
//			labelPic.setIcon(bg);
//			labelPic.setBounds(5,55,bg.getIconWidth(),bg.getIconHeight()); //���ͼƬ��frame�ĵڶ���
//			drag.setSize(bg.getIconWidth(),bg.getIconHeight());
//			super.add(labelPic);
//			int[] rgb = new int[3];
//			BufferedImage bi = null;
//			txt_Numb1.setText(file.getAbsolutePath());
//			try
//			{
//				bi = ImageIO.read(file);
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			this.repaint();//ҳ��ˢ��
//			img=imgToArray.convertImageToArray(bi);
//			btn_CutFig.setEnabled(true);
//		}
//		if (arg0.getSource() == btn_CutFig)
//		{
//			Lx=drag.Lx;
//			Ly=drag.Ly;
//			Rx=drag.Rx;
//			Ry=drag.Ry;
//			Lx=Lx*4;
//			Ly=Ly*4;
//			Rx=Rx*4;
//			Ry=Ry*4;
//			System.out.println("Lx:"+Lx);
//			System.out.println("Ly:"+Ly);
//			System.out.println("Rx:"+Rx);
//			System.out.println("Ry:"+Ry);
//			//�����ȡͼ�εĴ�С ���� �� ���
//			int PicWidth=0,PicHeight=0;
//			PicWidth=(Rx-Lx);
//			PicHeight=(Ry-Ly);
//			img_TiQu=new int[PicHeight][PicWidth];
//			System.out.println(img.length);
//			for (int i = 0; i < PicHeight; i++)
//			{
//				for (int j = 0; j < PicWidth; j++)
//				{
//					img_TiQu[i][j]=img[Ly+i][Lx+j];
//				}
//			}
//			imgToArray.writeImageFromArray("D:\\1.jpg", "jpg", img_TiQu);
//			c_OtsuYuZhi Pro_fig=new c_OtsuYuZhi();//otsu ��ֵ�ָ�
//			int T=0;//��ֵ
//			T=Pro_fig.otsuThresh(img_TiQu, PicWidth, PicHeight);
//			int pic[][]=new int[PicHeight][PicWidth];
//			pic=Pro_fig.thSegment(img_TiQu, PicWidth, PicHeight, T);
//			imgToArray.writeImageFromArray("D:\\2.jpg", "jpg", pic);
//			c_GetArea getArea=new c_GetArea();
//			irect=getArea.CountPix(pic, PicWidth, PicHeight);
//			System.out.println("�������ص�:"+irect);
//			btn_CutFig.setEnabled(false);
//			btn_SAThings.setEnabled(true);
//			btn_CalculateArea.setEnabled(false);
//		}
//		if (arg0.getSource() == btn_SAThings)
//		{
//			Lx=drag.Lx;
//			Ly=drag.Ly;
//			Rx=drag.Rx;
//			Ry=drag.Ry;
//			Lx=Lx*4;
//			Ly=Ly*4;
//			Rx=Rx*4;
//			Ry=Ry*4;
//			System.out.println("Lx:"+Lx);
//			System.out.println("Ly:"+Ly);
//			System.out.println("Rx:"+Rx);
//			System.out.println("Ry:"+Ry);
//			//�����ȡͼ�εĴ�С ���� �� ���
//			int PicWidth=0,PicHeight=0;
//			PicWidth=(Rx-Lx);
//			PicHeight=(Ry-Ly);
//			img_TiQu=new int[PicHeight][PicWidth];
//			System.out.println(img.length);
//			for (int i = 0; i < PicHeight; i++)
//			{
//				for (int j = 0; j < PicWidth; j++)
//				{
//					img_TiQu[i][j]=img[Ly+i][Lx+j];
//				}
//			}
//			imgToArray.writeImageFromArray("D:\\3.jpg", "jpg", img_TiQu);
//			int T=0;//��ֵ
//			c_OtsuYuZhi Pro_fig=new c_OtsuYuZhi();//otsu ��ֵ�ָ�
//			T=Pro_fig.otsuThresh(img_TiQu, PicWidth, PicHeight);
//			T=Pro_fig.otsuThresh(img_TiQu, PicWidth, PicHeight);
//			int pic[][]=new int[PicHeight][PicWidth];
//			pic=Pro_fig.thSegment(img_TiQu, PicWidth, PicHeight, T);
//			System.out.println("2����ֵ:"+T);
//			imgToArray.writeImageFromArray("D:\\4.jpg", "jpg", pic);
//			c_GetArea getArea=new c_GetArea();
//			iLeaf=getArea.CountPix(pic, PicWidth, PicHeight);
//			System.out.println("Ҷ�����ص�:"+iLeaf);
//			btn_CutFig.setEnabled(false);
//			btn_SAThings.setEnabled(false);
//			btn_CalculateArea.setEnabled(true);
//		}
//		if (arg0.getSource() == btn_CalculateArea)
//		{
//			double dbArea=(iLeaf*1.0)/(irect*1.0);
//			System.out.println("Ҷ�����:"+dbArea);
//			img=null;//����ͼ������
//			img_TiQu=null;//��ȡ����������(ѡ���ڵ�)
//			iLeaf=0;
//			irect=0;
//			Lx=0;
//			Ly=0;
//			Rx=0;
//			Ry=0;
//			im2Ar=null;
//			bg=null;
//			int_Shink=0;
//		}
//	}
//}
