package com.floreantpos.util;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialPortUtil {
	public static String readWeight(String comPort) throws SerialPortException {
		final SerialPort serialPort = new SerialPort(comPort);
		serialPort.openPort();//Open serial port
		serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_2, SerialPort.PARITY_EVEN);
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT | SerialPort.FLOWCONTROL_XONXOFF_IN
				| SerialPort.FLOWCONTROL_XONXOFF_OUT);

		final StringBuilder messageBuilder = new StringBuilder();

		serialPort.addEventListener(new SerialPortEventListener() {
			@Override
			public void serialEvent(SerialPortEvent event) {
				try {
					if (event.isRXCHAR() && event.getEventValue() > 0) {
						byte buffer[] = serialPort.readBytes();
						for (byte b : buffer) {
							if ((b == '\r' || b == '\n') && messageBuilder.length() > 0) {
								serialPort.closePort();
								break;
							}
							else {
								messageBuilder.append((char) b);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		byte[] data = new byte[] { 0x57, 0x0D, 0 };
		serialPort.writeBytes(data);
		
		synchronized (messageBuilder) {
			try {
				messageBuilder.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return messageBuilder.toString();
	}
}
