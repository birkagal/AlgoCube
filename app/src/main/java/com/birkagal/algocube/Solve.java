package com.birkagal.algocube;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Solve implements Comparable<Solve> {
    private Long solveTime;
    private Date solveDate;

    public Solve() {
    }

    public Solve(long time) {
        this.solveTime = time;
        this.solveDate = new Date();
    }

    public Long getSolveTime() {
        return this.solveTime;
    }

    public String getFormattedTime() {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(this.solveTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(this.solveTime);
        long millis = (this.solveTime / 10) % 100;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, millis);
    }

    public Date getSolveDate() {
        return solveDate;
    }

    @Override
    public int compareTo(@NotNull Solve solve) {
        return this.solveTime.compareTo(solve.getSolveTime());
    }
}
