package DHRS;

import java.util.ArrayList;
import java.util.HashMap;
public class Function_implement extends functionPOA {
	HashMap<Integer, Room> reservationinfo=new HashMap<Integer, Room>();
	ArrayList<Room> Roominfo = new ArrayList<Room>();
	HashMap<Integer, Guest> reservationcheck=new HashMap<Integer, Guest>();
	int ReservavtionID=0;
	String hroominfo="";
	String serviceReport="";
	String printstaue="";
	String transferinfo="";
	public Function_implement(int a,int b,int c){
		for(int i=0;i<a;i++){
			Roominfo.add(new Room(a,"Single",false,0,0,null));
		}
		for(int i=0;i<b;i++){
			Roominfo.add(new Room(b,"Double",false,0,0,null));
		}
		for(int i=0;i<c;i++){
			Roominfo.add(new Room(c,"Family",false,0,0,null));
		}
	}
	private void RoomnumAdown(String RoomType){
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room.equals(RoomType)){
				Roominfo.get(i).No_of_room--;
			}
		}
	}
	private String checkroom(String Roomtype){
		int rent=0;
		int Available=0;
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room.equals(Roomtype)){
				if(!Roominfo.get(i).Rent_of_room){
					Available++;
				}
				if(Roominfo.get(i).Rent_of_room){
					rent++;
				}
			}
		}
		//Available=Available-rent;
		return "Available "+Available+" Rent: "+rent;

	}
	private void RoomnumAup(String RoomType){
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room==RoomType){
				Roominfo.get(i).No_of_room++;
			}
		}
	}

	public String reserveRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate, int reserveID) {
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Type_of_room.equals(RoomType)){
				if(!Roominfo.get(i).Rent_of_room){
					Roominfo.get(i).Guests=new Guest(GuestID,Roominfo.get(i));
					Roominfo.get(i).Checkindate=checkindate;
					Roominfo.get(i).Checkoutdate=checkoutdate;
					Roominfo.get(i).Rent_of_room=true;
					RoomnumAdown(RoomType);
					reservationinfo.put(GuestID, Roominfo.get(i));
					ReservavtionID++;
					reservationcheck.put(ReservavtionID, Roominfo.get(i).Guests);
					System.out.println();
					break;
				}
			}
		}
		return "ResertionRoom Successful!GuesrID: "+GuestID+ "With ReservavtionID: "+ReservavtionID;

	}

	public String cancelRoom(int GuestID, String hotel, String RoomType,
			int checkindate, int checkoutdate) {
		if(reservationinfo.containsKey(GuestID)){
			for(int i=0;i<Roominfo.size();i++){
				if(Roominfo.get(i).Type_of_room.equals(RoomType)&&Roominfo.get(i).Checkindate==checkindate&&
						Roominfo.get(i).Checkoutdate==checkoutdate){

					Roominfo.get(i).Rent_of_room=false;
					Roominfo.get(i).Guests=null;
					Roominfo.get(i).Checkindate=0;
					Roominfo.get(i).Checkoutdate=0;
					RoomnumAup(RoomType);
					reservationinfo.remove(GuestID);
		
				}
			}
		}
		return "Cancel room successful! GuestID: "+GuestID+"with Hotel: "+hotel+" Type of Room: "+RoomType;

	}

	public String checkAvailability(int GuestID, String Preferredhotel,
			String RoomType, int checkindate, int checkoutdate) {
		hroominfo+=checkroom(RoomType)+"\n";
		return hroominfo;
	}

	public String serviceReport(String hotel, int ServiceDate) {
		serviceReport="";
		for(int i=0;i<Roominfo.size();i++){
			if(Roominfo.get(i).Checkoutdate==ServiceDate){
				serviceReport +=i+1+"RoomType: "+Roominfo.get(i).Type_of_room+","+"Checkindate: "+Roominfo.get(i).Checkindate+"Checkoutdate: "+Roominfo.get(i).Checkoutdate+"\n";
				System.out.println(serviceReport);
			}
		}

		return serviceReport;
	}

	public String printSatus(String hotel, int Date) {
		printstaue="";
		for(int i=0;i<Roominfo.size();i++){
			printstaue+=i+1+". "+"RoomType: "+Roominfo.get(i).Type_of_room+"Rent: "+Roominfo.get(i).Rent_of_room;
			if(Roominfo.get(i).Guests!=null){
			printstaue+="Checkindate: "+Roominfo.get(i).Checkindate+"Checkoutdate: "+Roominfo.get(i).Checkoutdate+"Rent GuestID: "+Roominfo.get(i).Guests.toString()+"\n";
			}else{
			printstaue+="Not Rent\n";
			}
		}
		return printstaue;
		
	}

	public String transferReservation(int GuestID, int ReservationID,
			String CurrentHotel, String OtherHotel) {
		if(reservationcheck.containsKey(ReservationID)){
			for(int i=0;i<Roominfo.size();i++){
				if(Roominfo.get(i).Guests.Guestid==GuestID){
					transferinfo+=Roominfo.get(i).Guests.Guestid+",";
					transferinfo+=OtherHotel+",";
					transferinfo+=Roominfo.get(i).Type_of_room+",";
					transferinfo+=Roominfo.get(i).Checkindate+",";
					transferinfo+=Roominfo.get(i).Checkoutdate;
					Roominfo.get(i).Rent_of_room=false;
					Roominfo.get(i).Guests=null;
					Roominfo.get(i).Checkindate=0;
					Roominfo.get(i).Checkoutdate=0;
					RoomnumAup(Roominfo.get(i).Type_of_room);
					reservationinfo.remove(GuestID);
//					cancelRoom(GuestID,CurrentHotel,Roominfo.get(i).Type_of_room,Roominfo.get(i).Checkindate,
//							Roominfo.get(i).Checkoutdate);
					break;
				}
			}
		}
         return transferinfo;
	}


}
