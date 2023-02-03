package dao;

public interface TicketDao {
	
	public String inserttrainDetails(String trainname,String boarding, String destination,
			String traintype, int Seats, String ArrivalTime, String DepartureTime, String trainno);
	
	public String confirmTicket(String boarding,String destination,String name,int age,String trainname);
	
	public String ticketCancel(String trainname, String trainno);
	
	public String adminLogin(String username, String password);
	
	
}