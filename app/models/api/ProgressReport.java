package models.api;

public class ProgressReport {

	private double planning;
	private double nieuwInzicht;
	private double hoursTodo;
	private double hoursWorked;
	private double hoursTotal;
	private double percentageGereed;
	private double afwijking;
	private double percentageAfwijking;
	private String lastUpdateDate;
	
	public ProgressReport(double hoursWorked, double hoursRealised, double hoursPlanned, double hoursTodo, double hoursTotal, String lastUpdateDate) {		
		this.planning = hoursPlanned;
		this.afwijking = (hoursPlanned - hoursRealised - hoursTodo);
		this.hoursTodo = hoursTodo;
		this.hoursWorked = hoursWorked;
		this.hoursTotal = hoursTotal;
		this.nieuwInzicht = (this.planning - this.afwijking);
		this.percentageGereed = roundToDecimals( ((this.nieuwInzicht - hoursTodo) / this.nieuwInzicht * 100) , 2);
		if(this.afwijking == 0.0 && this.planning == 0.0) {
			this.percentageAfwijking = 0.0;
		}
		else {
			this.percentageAfwijking = roundToDecimals( ( (Math.abs(this.afwijking) / this.planning) * 100 ), 2);
		}
		this.lastUpdateDate = lastUpdateDate;
	}

	public double getPlanning() {
		return planning;
	}

	public double getNieuwInzicht() {
		return nieuwInzicht;
	}

	public double getHoursTodo() {
		return hoursTodo;
	}

	public double getHoursWorked() {
		return hoursWorked;
	}

	public double getHoursTotal() {
		return hoursTotal;
	}

	public double getPercentageGereed() {
		return percentageGereed;
	}

	public double getAfwijking() {
		return afwijking;
	}

	public double getPercentageAfwijking() {
		return percentageAfwijking;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public static double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
	}
}
