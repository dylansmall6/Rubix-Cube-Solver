package com.dylanscode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class Face
{
	private int x;
	private int y;
	private int mult;
	private Color[] colors;
	private Rectangle[] boundaries;
	private Map<Rectangle, Color> colormap;

	public Face(int x, int y, int mult, Color centerColor)
	{
		this.x = x;
		this.y = y;
		this.mult = mult;
		colors = new Color[9];
		for (int i = 0; i < colors.length; i++)
		{
			if (i != 4)
			{
				colors[i] = Color.WHITE;
			} else
			{
				colors[i] = centerColor;
			}
		}
		boundaries = createBoundaries();
		colormap = new HashMap<>();
		for (int i = 0; i < 9; i++)
		{
			colormap.put(boundaries[i], colors[i]);
			System.out.println("[Debug] Put the color: " + colormap.get(boundaries[i]) + " to the rectangle at: ("
					+ boundaries[i].x + "," + boundaries[i].y + ")");
		}
	}

	public void tick()
	{
	}

	/**
	 * found it easier to use a 2d arrray then convert into a 1d array for some
	 * reason... oh well it works though!
	 * 
	 * @return
	 */
	public Rectangle[] createBoundaries()
	{
		Rectangle[][] boundaries = new Rectangle[3][3];
		for (int i = 0; i < boundaries.length; i++)
		{
			for (int j = 0; j < boundaries[i].length; j++)
			{
				boundaries[i][j] = new Rectangle(x + (j * mult), y + (i * mult), mult, mult);
				System.out.println("[Debug] Rect: (" + (x + (j * mult)) + "," + (y + (i * mult)) + ") to ("
						+ (x + ((j + 1) * mult)) + "," + (y + ((i + 1) * mult)) + ")");
			}
		}
		Rectangle[] oneDimensionalBoundaries = new Rectangle[9];
		oneDimensionalBoundaries[0] = boundaries[0][0];
		oneDimensionalBoundaries[1] = boundaries[0][1];
		oneDimensionalBoundaries[2] = boundaries[0][2];
		oneDimensionalBoundaries[3] = boundaries[1][0];
		oneDimensionalBoundaries[4] = boundaries[1][1];
		oneDimensionalBoundaries[5] = boundaries[1][2];
		oneDimensionalBoundaries[6] = boundaries[2][0];
		oneDimensionalBoundaries[7] = boundaries[2][1];
		oneDimensionalBoundaries[8] = boundaries[2][2];
		return oneDimensionalBoundaries;
	}

	public void draw(Graphics2D g)
	{
		for (int i = 0; i < 9; i++)
		{
			g.setColor(colormap.get(boundaries[i]));
			g.fill(boundaries[i]);
		}
		g.setColor(Color.BLACK);
		// drawing vertical lines
		g.drawLine(x, y, x, y + (3 * mult));
		g.drawLine(x + (mult), y, x + mult, y + (3 * mult));
		g.drawLine(x + (2 * mult), y, x + (2 * mult), y + (3 * mult));
		g.drawLine(x + (3 * mult), y, x + (3 * mult), y + (3 * mult));
		// drawing horizontal lines
		g.drawLine(x, y, x + (3 * mult), y);
		g.drawLine(x, y + mult, x + (3 * mult), y + mult);
		g.drawLine(x, y + (2 * mult), x + (3 * mult), y + (2 * mult));
		g.drawLine(x, y + (3 * mult), x + (3 * mult), y + (3 * mult));
	}

	public Rectangle getRectFromPoint(Point p)
	{
		for (Rectangle rect : boundaries)
		{
			if (rect.contains(p))
			{
				return rect;
			}
		}
		return null;
	}

	public Map<Rectangle, Color> getColorMap()
	{
		return colormap;
	}
	public Rectangle[] getBoundaries() {
		return boundaries;
	}
}
