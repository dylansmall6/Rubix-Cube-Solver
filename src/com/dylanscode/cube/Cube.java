package com.dylanscode.cube;

import java.awt.Color;

import com.dylanscode.Face;

public class Cube
{
	public static Face[] generateFaces()
	{
		Face[] faces = new Face[6];
		faces[0] = new Face(100, 113, 50,Color.RED);
		faces[1] = new Face(350, 113, 50,Color.ORANGE);
		faces[2] = new Face(100, 375, 50,Color.BLUE);
		faces[3] = new Face(350, 375, 50,Color.GREEN);
		faces[4] = new Face(100, 638, 50,Color.YELLOW);
		faces[5] = new Face(350, 638, 50,Color.WHITE);
		return faces;
	}
}
