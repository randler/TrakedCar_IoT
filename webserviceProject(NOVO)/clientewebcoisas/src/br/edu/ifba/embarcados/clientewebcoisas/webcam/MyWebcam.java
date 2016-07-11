package br.edu.ifba.embarcados.clientewebcoisas.webcam;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

public class MyWebcam extends javax.swing.JFrame implements Runnable,Thread.UncaughtExceptionHandler, WindowListener, WebcamListener, ItemListener, WebcamDiscoveryListener {
	
    private WebcamPicker seletor = null;
    private Webcam webcam = null;
    private WebcamPanel painel = null;
    private static MyWebcam minhaWeb = null;
	
    private MyWebcam(){
        
    }
    
    public static MyWebcam getInstancia(){
        if(minhaWeb == null){
            minhaWeb = new MyWebcam();
        }
        return minhaWeb;
    }
    
    public void executarWebcam() {
        SwingUtilities.invokeLater(new MyWebcam().getInstancia());
       }
    
    public Image tirarFoto(){
    	 Image imagem = webcam.getImage();
//         ImageIcon img = new ImageIcon(imagem);
//         jLabel1.setIcon(img);
//         jLabel1.setSize(imagem.getWidth(painel), imagem.getHeight(painel));
    
         return imagem;
    }
    
    

	@Override
	public void webcamFound(WebcamDiscoveryEvent arg0) {
		if(null != seletor){
			seletor.addItem(arg0.getWebcam());
		}

	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent arg0) {
		if(null == seletor){
			seletor.removeItem(arg0.getWebcam());
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getItem() != webcam){
			if(null != webcam){
				painel.stop();
				
				remove(painel);
				
				webcam.removeWebcamListener(this);
				webcam.close();
				
				webcam = (Webcam) e.getItem();
				webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);
				
				painel = new WebcamPanel(webcam, false);
				painel.setFPSDisplayed(true);
				add(painel, BorderLayout.CENTER);
				pack();
				
				setVisible(true);
				
				Thread t = new Thread(){
					
					@Override
					public void run(){
						painel.start();
					}
				};
				
				t.setName("Thread Change Webcam PainelStart");
				t.setDaemon(true);
				t.setUncaughtExceptionHandler(this);
				t.start();
				
			}
		}
	
	}

	@Override
	public void webcamClosed(WebcamEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void webcamDisposed(WebcamEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void webcamImageObtained(WebcamEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void webcamOpen(WebcamEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		webcam.close();
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		painel.pause();
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		painel.resume();
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(String.format("Exception in Thread $s", t.getName()));
		e.printStackTrace();
	
	}

	@Override
	public void run() {
		 Webcam.addDiscoveryListener(this);
	        
	        setTitle("Minha Webcam");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());
	        
	        addWindowListener(this);
	        
	        seletor = new WebcamPicker();
	        seletor.addItemListener(this);
	        webcam = seletor.getSelectedWebcam();
	        
	        if(null == webcam){
	            System.out.println("Webcam not found!");
	            System.exit(1);
	        }
	        
	        painel = new WebcamPanel(webcam, false);
	        painel.setFPSDisplayed(true);
	        add(seletor, BorderLayout.NORTH);
	        add(painel, BorderLayout.CENTER);
	        
	        pack();
	        setVisible(true);
	        
	 
	        Thread t = new Thread(){
	          
	            @Override
	            public void run(){
	                painel.start();                
	            }            
	        };
	        
	        t.setName("exemplo do start");
	        t.setDaemon(true);
	        t.setUncaughtExceptionHandler(this);
	        t.start();
	        

	}

}