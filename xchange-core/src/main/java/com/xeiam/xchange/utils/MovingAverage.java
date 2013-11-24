package com.xeiam.xchange.utils;

import java.util.Arrays;

public class MovingAverage {

	int windowSize;
	float total = 0;
	int index = 0;
	int firstIndex = 0;
	float[] window;

	public MovingAverage() {
		this(30);
	}

	public MovingAverage(int aWindowSize) {
		windowSize = aWindowSize;
		window = new float[windowSize];
		Arrays.fill(window, 0);
	}

	public float addSample(float aSample) {
		total -= window[index];
		total += aSample;
		window[index] = aSample;
		++index;
		if (firstIndex < windowSize) {
			++firstIndex;
		}
		if (index == windowSize) {
			index = 0;
		}
		return getAverage();
	}

	public float getAverage() {
		return total / Math.min(windowSize, firstIndex);
	}

}
