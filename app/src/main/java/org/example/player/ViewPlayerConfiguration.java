package org.example.player;

public class ViewPlayerConfiguration {
    private int numberOfRays;
    private float fieldOfView;

    public ViewPlayerConfiguration(int numberOfRays, float fieldOfView) {
        this.numberOfRays = numberOfRays;
        this.fieldOfView = fieldOfView;
    }

	public int getNumberOfRays() { return numberOfRays; }
	public float getFieldOfView() { return fieldOfView;	}
}
