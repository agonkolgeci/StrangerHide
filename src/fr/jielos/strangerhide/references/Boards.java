package fr.jielos.strangerhide.references;

public enum Boards {

    MAIN("§c§lStrangerHide", "§emc.StrangerHide.ga");

    final String displayName;
    final String footer;
    Boards(final String displayName, final String footer) {
        this.displayName = displayName;
        this.footer = footer;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getFooter() {
        return footer;
    }
}