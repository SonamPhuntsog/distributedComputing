package distributedcomputing;
import java.net.*;
import java.util.*;
/*
This interface is used so that RegisterServer thread can call following methods
implemented in UnitHub

*/
public interface IRegister {
  public int getRegisterSize();
  public List<InetSocketAddress> getRegister();
  public void addToRegister(InetSocketAddress addr);
  public void removeFromRegister(InetSocketAddress addr);
  public String printRegister();

}
