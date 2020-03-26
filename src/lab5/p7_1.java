//�����������ʱ��Ҫ��Java��Java 3D �İ�
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.image.*;
import java.awt.GraphicsConfiguration;
import java.util.BitSet;
public class p7_1 extends Applet implements ActionListener
{		
	String filename;
	BitSet mask=null;
	Switch switchgroup=null;
	public static void main(String [] args)
	{
		//ͨ��MainFrame ��ʾͼ��
		new MainFrame(new p7_1(),600,600);
	}
	public p7_1()
	{
		//������ʾ�������ز���
		setLayout(new BorderLayout());
		Panel pane1=new Panel();
		pane1.setLayout(new GridLayout(1,4));
		add(pane1,BorderLayout.SOUTH);
		Button button=new Button("BOX");
		button.addActionListener(this);
		pane1.add(button);
		button=new Button("SPHERE");
		button.addActionListener(this);
		pane1.add(button);
		button=new Button("CYLINDER");
		button.addActionListener(this);
		pane1.add(button);
		button=new Button("DODECAHEDRON");
		button.addActionListener(this);
		pane1.add(button);
		button=new Button("BEZIERSURFACESHAPE");
		button.addActionListener(this);
		pane1.add(button);
		//����ͶӰƽ��Canvas3D
		GraphicsConfiguration gc=SimpleUniverse.getPreferredConfiguration();
		Canvas3D c=new Canvas3D(gc);
		//��ͶӰƽ���ϵ�ͼ����ʾ��ƽ����м�
		add("Center",c);
		//����SimpleUniverse����ϵͳѡ���ӵ���z������򣬹۲췽����z�ᷴ��
		BranchGroup BrachGroupScene=creatBranchGroup();
		SimpleUniverse u=new SimpleUniverse(c);
		u.getViewingPlatform().setNominalViewingTransform();
		//��BranchGroup: BrachGroupScene���뵽SimpleUniverse��u��
		u.addBranchGraph(BrachGroupScene);
		
	}

	public BranchGroup creatBranchGroup()
	{
		//����BranchGroup
		BranchGroup BranchGroupRoot=new BranchGroup();
		BranchGroupRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		Transform3D t=new Transform3D();
		t.setTranslation(new Vector3f(0.f,0.1f,0.f));
		
		//�����ܵ�TransformGroup:transformgroup
		TransformGroup transformgroup=new TransformGroup(t);
		//����TransformGroup�Ķ�д����
		transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformgroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

		//����TransformGroup���뵽��BranchGroupRoot
		BranchGroupRoot.addChild(transformgroup);
		//��������������ϵԭ�㷶Χ
		BoundingSphere bounds=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
		//���屳����ɫ
		Color3f bgColor=new Color3f(1.0f,1.0f,1.0f);
		Background bg=new Background(bgColor);
		bg.setApplicationBounds(bounds);
		BranchGroupRoot.addChild(bg);
		//�������Գ�������ת��ƽ����Ŵ���
		MouseRotate mouserotate=new MouseRotate();
		mouserotate.setTransformGroup(transformgroup);
		BranchGroupRoot.addChild(mouserotate);
		mouserotate.setSchedulingBounds(bounds);
		
		MouseZoom mousezoom=new MouseZoom();
		mousezoom.setTransformGroup(transformgroup);
		BranchGroupRoot.addChild(mousezoom);
		mousezoom.setSchedulingBounds(bounds);
		
		MouseTranslate mousetranslate=new MouseTranslate();
		mousetranslate.setTransformGroup(transformgroup);
		BranchGroupRoot.addChild(mousetranslate);
		mousetranslate.setSchedulingBounds(bounds);
		
		//����ƽ�й⡢��ɫ�����䷽�������÷�Χ
		Color3f directionalColor=new Color3f(1.0f,1.f,1.f);
		Vector3f vec=new Vector3f(-1.f,-1.f,-1.f);
		DirectionalLight directionalLight=new DirectionalLight(directionalColor,vec);
		directionalLight.setInfluencingBounds(bounds);
		BranchGroupRoot.addChild(directionalLight);
		
		filename="earth.jpg";Appearance app= createTextureAppearance(filename);
		Sphere shape1=new Sphere(0.8f,Sphere.GENERATE_NORMALS|Sphere.GENERATE_TEXTURE_COORDS,100,app);
		filename="earth.jpg";
		app= createTextureAppearance(filename);
		Cylinder shape2=new Cylinder(0.7f,1.3f,Cylinder.GENERATE_NORMALS|Cylinder.GENERATE_TEXTURE_COORDS,200,200,app);
		filename="earth.jpg";
		app= createTextureAppearance(filename);
		Box shape3=new Box(0.6f,0.6f,0.6f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,app);
		filename="earth.jpg";
		app= createTextureAppearance(filename);
		Cone shape4=new Cone (0.9f,1.f,Cone.GENERATE_NORMALS|Cone.GENERATE_TEXTURE_COORDS,200,200,app);
		switchgroup=new Switch();
		switchgroup.setWhichChild(switchgroup.CHILD_MASK);
		switchgroup.setCapability(switchgroup.ALLOW_SWITCH_WRITE);
		switchgroup.addChild(shape1);	switchgroup.addChild(shape2);
		switchgroup.addChild(shape3);	switchgroup.addChild(shape4);
		mask=new BitSet();
	 	mask.set(0);
		switchgroup.setChildMask(mask);
		switchgroup.setWhichChild(Switch.CHILD_MASK);
		transformgroup.addChild(switchgroup);
		return BranchGroupRoot;


	
	//    transformgroup.addChild(switchgroup);
	//	return BranchGroupRoot;
		
	}
	public Appearance createTextureAppearance(String filename)
	{
		Appearance app=new Appearance();
		TextureLoader loader=new TextureLoader(filename,this);
		ImageComponent2D image1=loader.getImage();
		Texture2D texure=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image1.getWidth(),image1.getHeight());
		texure.setImage(0,image1);
		texure.setEnable(true);
		texure.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texure.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		app.setTexture(texure);
		
		PolygonAttributes polygonattributes=new PolygonAttributes();
		polygonattributes.setCullFace(PolygonAttributes.CULL_NONE);
		polygonattributes.setBackFaceNormalFlip(true);
		app.setPolygonAttributes(polygonattributes);
		app.setCapability(Appearance.ALLOW_TEXGEN_WRITE);
		
		return app;
	}
	
		public void actionPerformed(ActionEvent e)
	{
		String cmd=e.getActionCommand();
		mask=new BitSet();
		
		if("SPHERE".equals(cmd)) mask.set(0);
		else if("CYLINDER".equals(cmd)) mask.set(1);
		else if("DODECAHEDRON".equals(cmd)) mask.set(2);
		else if("BEZIERSURFACESHAPE".equals(cmd)) mask.set(3);
		switchgroup.setChildMask(mask);
		switchgroup.setWhichChild(Switch.CHILD_MASK);
	}

}
