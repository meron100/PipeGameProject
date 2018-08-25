package algorithm;

public class PipeGameGrader implements StateGrader<Charmatrix> {

	@Override
	public int Grade(State<Charmatrix> s) {
	int len=(s.state.getMatrix().length*s.state.getMatrix()[0].length) +10;
		return (int) (len-s.getCost());
	}

}
