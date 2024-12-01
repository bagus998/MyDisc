class Song {
    String title;
    String artist;
    int duration; // in seconds
    String albumImagePath; // Path to album image

    Song(String title, String artist, int duration, String albumImagePath) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.albumImagePath = albumImagePath;
    }

    // Format duration as "minutes:seconds"
    String getFormattedDuration() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}