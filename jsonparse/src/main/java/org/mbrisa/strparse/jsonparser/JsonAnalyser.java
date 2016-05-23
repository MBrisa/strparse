package org.mbrisa.strparse.jsonparser;

import org.mbrisa.strparse.Analyser;
import org.mbrisa.strparse.State;
import org.mbrisa.strparse.jsonparser.state.InitState;

public class JsonAnalyser extends Analyser {

	@Override
	protected State initState() {
		return new InitState();
	}

}
