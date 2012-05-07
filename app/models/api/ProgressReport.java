package models.api;

public class ProgressReport {

	private double planning;
	private double niew_inzicht;
	private double hours_todo;
	private double hours_worked;
	private double hours_total;
	private double percentage_gereed;
	private double afwijking;
	private double percentage_afwijking;
	
	public ProgressReport(double hours_worked, double hours_realised, double hours_planned, double hours_todo) {
		this.planning = hours_planned;
		this.niew_inzicht = (hours_planned - hours_realised - hours_todo);
		this.hours_todo = hours_todo;
		this.hours_worked = hours_worked;
		this.hours_total = hours_planned;
		this.percentage_gereed = (this.niew_inzicht - hours_realised);
		this.afwijking = (this.planning - this.niew_inzicht);
		this.percentage_afwijking = ( (Math.abs(this.afwijking) / this.planning) * 100 );
	}

	public double getPlanning() {
		return planning;
	}

	public double getNiew_inzicht() {
		return niew_inzicht;
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
}
