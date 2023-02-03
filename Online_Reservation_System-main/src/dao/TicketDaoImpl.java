package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utility.DButil;

public class TicketDaoImpl implements TicketDao {

	@Override
	public String inserttrainDetails(String trainname,String boarding, String destination,
			String trainType, int Seats, String ArrivalTime, String DepartureTime,String trainno)
	{
		String message = "Not inserted !";

		try (Connection conn = DButil.provideConnection()) {

			PreparedStatement ps =  conn.prepareStatement("insert into traindetails values (?,?,?,?,?,?,?,?) ");

			ps.setString(1, trainname);
			ps.setString(2, boarding);
			ps.setString(3, destination);
			ps.setString(4, trainType);
			ps.setInt(5, Seats);
			ps.setString(6, ArrivalTime);
			ps.setString(7, DepartureTime);
			ps.setString(8, trainno);
			int x = ps.executeUpdate();

			if(x>0)
			{
				message = "Train Details Inserted Sucessfully !";
			}



		} catch (Exception e) {
			message = e.getMessage();
		}




		return message;
	}

	@Override
	public String confirmTicket(String boarding, String destination, String name,int age,String trainname) {

		String message = "Seat Not Available !";

		try (Connection conn = DButil.provideConnection()){

			PreparedStatement ps = conn.prepareStatement("select seats,trainno from traindetails where boarding=? and destination=?");

			ps.setString(1, boarding);
			ps.setString(2, destination);

			ResultSet rs = ps.executeQuery();
			System.out.println("hello");

			try {
				
				if(rs.next())
				{	System.out.println("Inside If");
					int x = rs.getInt("Seats");

					if(x>0)
					{	System.out.println("Values of X:"+x);
						PreparedStatement ps2 = conn.prepareStatement("Update traindetails set Seats=Seats-1 where boarding=?");

						ps2.setString(1, boarding);

						ps2.executeUpdate();

						PreparedStatement ps3 = conn.prepareStatement("insert into customerdetails values(?,?,?,?)");

						ps3.setString(1, rs.getString(2));
						ps3.setString(2,name);
						ps3.setInt(3, age);
						ps3.setString(4, rs.getString(1));
						ps3.executeUpdate();
						PreparedStatement ps4  = conn.prepareStatement("select * from customerdetails where name=?");

						ps4.setString(1, name);

						ResultSet rs2 = ps4.executeQuery();

						if(rs2.next())
						{
							System.out.println("XZZZZ");
							System.out.println("TrainNo: "+ rs2.getString(1));
							System.out.println("Passenger Name: "+rs2.getString(2));
							System.out.println("Passenger Age: "+rs2.getInt(3));	
							System.out.println("TrainName: "+rs2.getString(4));
						}
						message = "Ticked confirmed !";
					}

				}
				else 
				{
					message = "Please Enter Valid Location !";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


		} catch (Exception e) {
			message = e.getMessage();
		}
		return message;
	}

	@Override
	public String ticketCancel(String trainname, String trainno) {

		String message = "Ticket Canellation Unsucessful !";


		try (Connection conn = DButil.provideConnection()) {

			PreparedStatement ps = conn.prepareStatement("delete from customerdetails where trainname = ? and trainno = ?");

			ps.setString(1,trainname);
			ps.setString(2, trainno);

			int x= ps.executeUpdate();

			PreparedStatement ps2 = conn.prepareStatement("Update traindetails set seats = seats+1 where trainno = ?");

			ps2.setString(1, trainno);

			ps2.executeUpdate();

			if(x>0)
			{
				message = "Ticket Cancellation Sucessful !";
			}

		} catch (Exception e) {
			message = e.getMessage();
		}


		return message;
	}

	@Override
	public String adminLogin(String username,String password) {

		String message = "Wrong Credentials!";

		try (Connection conn = DButil.provideConnection()){

			PreparedStatement ps = conn.prepareStatement("Select * from adminlogin where username = ? and password = ?");

			ps.setString(1, username);
			ps.setString(2,password);

			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				System.out.println();
				System.out.println("Welcome "+rs.getString("username")+"!");
				message = "";
			}


		} catch (Exception e) {
			message = e.getMessage();
		}

		return message;
	}

}
