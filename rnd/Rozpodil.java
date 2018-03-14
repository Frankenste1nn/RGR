package rnd;

public enum Rozpodil {
	NEGEXP, NORM, ERLANG, UNIFORM, TRIANGULAR, LINEAR, DISCRET;
	 @Override public String toString() {
		   //only capitalize the first letter
		   String s = super.toString();
		   return s.substring(0, 1) + s.substring(1).toLowerCase();
		 }
}
