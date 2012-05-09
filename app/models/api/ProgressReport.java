package models.api;

public class ProgressReport {

	private double planning;
	private double nieuw_inzicht;
	private double hours_todo;
	private double hours_worked;
	private double hours_total;
	private double percentage_gereed;
	private double afwijking;
	private double percentage_afwijking;
	private String last_update_date;
	
	public ProgressReport(double hours_worked, double hours_realised, double hours_planned, double hours_todo, double hours_total, String last_update_date) {		
		this.planning = hours_planned;
		this.afwijking = (hours_planned - hours_realised - hours_todo);
		this.hours_todo = hours_todo;
		this.hours_worked = hours_worked;
		this.hours_total = hours_total;
		this.nieuw_inzicht = (this.planning - this.afwijking);
		this.percentage_gereed = roundToDecimals( ((this.nieuw_inzicht - hours_todo) / this.nieuw_inzicht * 100) , 2);
		if(this.afwijking == 0.0 && this.planning == 0.0) {
			this.percentage_afwijking = 0.0;
		}
		else {
			this.percentage_afwijking = roundToDecimals( ( (Math.abs(this.afwijking) / this.planning) * 100 ), 2);
		}
		this.last_update_date = last_update_date;
	}

	public double getPlanning() {
		return planning;
	}

	public double getNieuw_inzicht() {
		return nieuw_inzicht;
	}

	public double getHours_todo() {
		return hours_todo;
	}

	public double getHours_worked() {
		return hours_worked;
	}

	public double getHours_total() {
		return hours_total;
	}

	public double getPercentage_gereed() {
		return percentage_gereed;
	}

	public double getAfwijking() {
		return afwijking;
	}

	public double getPercentage_afwijking() {
		return percentage_afwijking;
	}

	public String getLast_update_date() {
		return last_update_date;
	}
	
	public static double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
	}
}
