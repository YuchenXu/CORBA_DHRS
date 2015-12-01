package DHRS_Corba;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class DHRS_Server {
	/**
	 * @param args
	 * @throws WrongPolicy 
	 * @throws ServantAlreadyActive 
	 * @throws ObjectNotActive 
	 * @throws FileNotFoundException 
	 * @throws AdapterInactive 
	 */
	public static void main(String[] args) throws InvalidName, ServantAlreadyActive, WrongPolicy, ObjectNotActive, FileNotFoundException, AdapterInactive {

		ORB orb=ORB.init(args,null);
		POA rootpoa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		Function_implement H1=new Function_implement(5,4,3);
		Function_implement H2=new Function_implement(5,4,3);
		Function_implement H3=new Function_implement(5,4,3);
		byte[] id1=rootpoa.activate_object(H1);
		byte[] id2=rootpoa.activate_object(H2);
		byte[] id3=rootpoa.activate_object(H3);
		org.omg.CORBA.Object ref1=rootpoa.id_to_reference(id1);
		org.omg.CORBA.Object ref2=rootpoa.id_to_reference(id2);
		org.omg.CORBA.Object ref3=rootpoa.id_to_reference(id3);
		String ior1=orb.object_to_string(ref1);
		String ior2=orb.object_to_string(ref2);
		String ior3=orb.object_to_string(ref3);
		System.out.println(ref1);System.out.println(ref2);System.out.println(ref3);
		PrintWriter file1=new PrintWriter("ior1.txt");
		PrintWriter file2=new PrintWriter("ior2.txt");
		PrintWriter file3=new PrintWriter("ior3.txt");
		file1.println(ior1);
		file1.close();
		file2.println(ior2);
		file2.close();
		file3.println(ior3);
		file3.close();
		rootpoa.the_POAManager().activate();
		orb.run();


	}

}
