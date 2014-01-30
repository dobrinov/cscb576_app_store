package server;

import java.util.EnumSet;
import java.util.Set;

public enum ServerState {

	STARTED {
		@Override
		public Set<ServerState> possibleFollowUps() {
		    return EnumSet.of(STOPPED, PAUSED);
		}
	},
	
	STOPPED {
		@Override
		public Set<ServerState> possibleFollowUps() {
		    return EnumSet.of(STARTED);
		}
	},
	
	PAUSED {
		@Override
		public Set<ServerState> possibleFollowUps() {
		    return EnumSet.of(STARTED);
		}
	};
	
	public Set<ServerState> possibleFollowUps() {
        return EnumSet.noneOf(ServerState.class);
    }
	
}