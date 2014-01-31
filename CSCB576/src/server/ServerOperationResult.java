package server;

public enum ServerOperationResult {
	SERVER_STARTED {
		@Override
		public String statusMessage(){
			return "The server has been started.";
		}
	},
	SERVER_STOPPED {
		@Override
		public String statusMessage(){
			return "The server has been stopped.";
		}
	},
	SERVER_RESTARTED {
		@Override
		public String statusMessage(){
			return "The server has been restarted.";
		}
	},
	SERVER_PAUSED {
		@Override
		public String statusMessage(){
			return "The server has been paused.";
		}
	},
	SERVER_RESUMED {
		@Override
		public String statusMessage(){
			return "The server has been resumed.";
		}
	},
	SERVER_OPERATION_NOT_POSSIBLE {
		@Override
		public String statusMessage(){
			return "The requested operation is not possible.";
		}
	},
	SERVER_STATE_STARTED {
		@Override
		public String statusMessage(){
			return "The server is started.";
		}
	},
	SERVER_STATE_STOPPED {
		@Override
		public String statusMessage(){
			return "The server is stopped.";
		}
	},
	SERVER_STATE_PAUSED {
		@Override
		public String statusMessage(){
			return "The server is paused.";
		}
	};
	
	public String statusMessage(){
		return "No server operation has been executed.";
	}
}