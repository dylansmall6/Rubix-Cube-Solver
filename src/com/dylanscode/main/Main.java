package com.dylanscode.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.dylanscode.Face;
import com.dylanscode.cube.Cube;
import com.dylanscode.input.MouseHandler;

public class Main extends JFrame implements Runnable
{
	private static final long serialVersionUID = 1L;
	public final int WIDTH = 600;
	public final int HEIGHT = 900;
	public boolean running = false;
	private BufferStrategy bs;
	private Graphics g;
	private Face[] faces;
	private MouseHandler mouseHandler;

	public Main()
	{
		setName("Rubix Cube Solver");
		setTitle("Rubix Cube Solver");
		Dimension size = new Dimension(WIDTH, HEIGHT);

		setVisible(true);
		setResizable(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		setLayout(new BorderLayout());

		pack();
		setLocationRelativeTo(null);
		createBufferStrategy(2);
	}

	/**
	 * Algorithm that calls tick() 60 times / second and paint(Graphics g) per
	 * frame. It then prints out the FPS + ticks
	 */
	public void run()
	{
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000 / 60D;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		init();
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (delta >= 1)
			{
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			// try {
			// Thread.sleep(0);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			if (shouldRender)
			{
				frames++;
				paint(g);
			}
			if (System.currentTimeMillis() - lastTimer > 1000)
			{
				lastTimer += 1000;
				System.out.println("[Debug] Frames: " + frames + " | Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void init()
	{
		mouseHandler = new MouseHandler(this);
		g = getGraphics();
		bs = getBufferStrategy();
		faces = Cube.generateFaces();
	}

	public void tick()
	{
		if (mouseHandler.LEFT_CLICK.isReleased())
		{
			for(Face face : faces) {
				Rectangle rect = face.getRectFromPoint(new Point(mouseHandler.LEFT_CLICK.getX(), mouseHandler.LEFT_CLICK.getY()));
				if (rect != null)
				{
					if (face.getColorMap().get(rect).equals(Color.WHITE) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.BLUE);
					} else if (face.getColorMap().get(rect).equals(Color.BLUE) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.RED);
					} else if (face.getColorMap().get(rect).equals(Color.RED) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.GREEN);
					} else if (face.getColorMap().get(rect).equals(Color.GREEN) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.ORANGE);
					} else if (face.getColorMap().get(rect).equals(Color.ORANGE) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.YELLOW);
					} else if(face.getColorMap().get(rect).equals(Color.YELLOW) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.WHITE);
					}
				}
			}
			mouseHandler.LEFT_CLICK.setReleased(false);
		}
	
		if (mouseHandler.RIGHT_CLICK.isReleased())
		{
			for(Face face : faces) {
				Rectangle rect = face
						.getRectFromPoint(new Point(mouseHandler.RIGHT_CLICK.getX(), mouseHandler.RIGHT_CLICK.getY()));
				System.out.print(rect);
				if (rect != null)
				{
					if (face.getColorMap().get(rect).equals(Color.WHITE) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.YELLOW);
					} else if (face.getColorMap().get(rect).equals(Color.YELLOW) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.ORANGE);
					} else if (face.getColorMap().get(rect).equals(Color.ORANGE) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.GREEN);
					} else if (face.getColorMap().get(rect).equals(Color.GREEN) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.RED);
					} else if (face.getColorMap().get(rect).equals(Color.RED) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.BLUE);
					} else if(face.getColorMap().get(rect).equals(Color.BLUE) && (face.getBoundaries()[4] != rect))
					{
						face.getColorMap().put(rect, Color.WHITE);
					}
				}
			}
			mouseHandler.RIGHT_CLICK.setReleased(false);
		}
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = null;
		if (bs != null)
		{
			do
			{
				try
				{
					g2 = (Graphics2D) bs.getDrawGraphics();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(Color.white);
					g2.fillRect(0, 0, getWidth(), getHeight());
					for (Face face : faces)
					{
						face.draw(g2);
					}
					g2.setFont(new Font("Serif",Font.PLAIN,20));
					g2.drawString("Click on the faces to change their color.", 140, 75);
					g2.drawString("Press [Enter] to solve.", 220, 850);
				} catch (Exception e)
				{
					System.err.println("[ERROR] [NON FATAL] There has been a non-fatal error, continue...");
					e.printStackTrace();
				} finally
				{
					if (g2 != null)
						g2.dispose();
				}
				bs.show();
			} while (bs.contentsLost());
		}
	}

	public synchronized void start()
	{
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop()
	{
		running = false;
	}

	public static void main(String[] args)
	{
		new Main().start();
	}
}
