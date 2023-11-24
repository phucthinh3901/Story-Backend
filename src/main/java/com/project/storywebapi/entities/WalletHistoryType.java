package com.project.storywebapi.entities;

public enum WalletHistoryType {
	
	Withdraw("Rút tiền"),
	Deposit( "Nạp tiền");
	
	public final String label;

	public String getLabel() {
		return label;
	}

	private WalletHistoryType(String label) {
		this.label = label;
	}
}
