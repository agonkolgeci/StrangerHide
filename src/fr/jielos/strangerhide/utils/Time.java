package fr.jielos.strangerhide.utils;

public class Time {

	public static String format(final int remainder) {
		int days = remainder / 86400;
		int hours = remainder / 3600;
		int minutes = remainder / 60;
		int seconds = remainder - (minutes * 60);
		
		return (
				(days > 0 ? days+"d" + (hours > 0 ? " " : "") : "") +
				(hours > 0 ? hours+"h" + (minutes > 0 ? " " : "") : "") +
				(minutes > 0 ? minutes+"m" + (seconds > 0 ? " " : "") : "") +
				(seconds > 0 ? seconds+"s" : "")
		);
	}
	
}
