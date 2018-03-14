package process;

import java.util.ArrayList;

public class MultiActor extends Actor {
	private Actor original;

	private int numberOfClones;

	private ArrayList<Actor> clonesArray = null;
	
	/**
	 * MultyActor constructor comment.
	 */
	public MultiActor() {
		super();
	}

	public MultiActor(String name, Actor a, int n) {
		super();
		setNameForProtocol(name);
		setOriginal((Actor) a);
		setNumberOfClones(n);
	}

	public void rule() {
		if(numberOfClones==0){
			System.out.println("Число клонів дорінює 0?");
			return;
		}
		if(original==null){
			System.out.println("Нема кого клонувати. original==null");
			return;
		}

		clonesArray = new ArrayList<Actor>();
		Actor clone;
		for (int i = 0; i < numberOfClones; i++) {
			clone = (Actor) original.clone();
			clone.setNameForProtocol(clone.getNameForProtocol() + (i + 1));
			clonesArray.add(clone);
			getDispatcher().addStartingActor(clone);
		}
	}

	public ArrayList<Actor> getClonesArray() {
		return clonesArray;
	}

	public void setNumberOfClones(int numberOfCopies) {
		this.numberOfClones = numberOfCopies;
	}


	public void setOriginal(Actor a) {
		original = a;
	}
}
