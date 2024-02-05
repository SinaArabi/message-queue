public class StatsResult {
    private int messageCount;
    private int totalMessageLength;
    private int totalProgramMemoryUsage;



    StatsResult(int messageCount, int totalMessageLength, int totalProgramMemoryUsage) {
        this.messageCount = messageCount;
        this.totalMessageLength = totalMessageLength;
        this.totalProgramMemoryUsage = totalProgramMemoryUsage;
    }

    public int getMessageCount() {
        return this.messageCount;
    }

    public int getTotalMessageLength() {
        return this.totalMessageLength;
    }

    public int getTotalProgramMemoryUsage() {
        return this.totalProgramMemoryUsage;
    }
}