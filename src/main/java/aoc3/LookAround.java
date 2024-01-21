package aoc3;

public enum LookAround {

    UP_LEFT((i, j, input) -> i > 0 && j > 0, -1, -1), UP((i, j, input) -> i > 0, -1, 0),
    UP_RIGHT((i, j, input) -> i > 0 && input[i - 1].length > j + 1, -1, 1),
    RIGHT((i, j, input) -> input[i].length > j + 1, 0, 1),
    DOWN_RIGHT((i, j, input) -> input.length > i + 1 && input[i + 1].length > j + 1, 1, 1),
    DOWN((i, j, input) -> input.length > i + 1, 1, 0), DOWN_LEFT((i, j, input) -> input.length > i + 1 && j > 0, 1, -1),
    LEFT((i, j, input) -> j > 0, 0, -1);

    private TriPredicate<Integer, Integer, char[][]> check;
    private int i_modification;
    private int j_modification;

    private LookAround(TriPredicate<Integer, Integer, char[][]> check, int i_modification, int j_modification) {
        this.check = check;
        this.i_modification = i_modification;
        this.j_modification = j_modification;
    }

    public TriPredicate<Integer, Integer, char[][]> getCheck() {
        return check;
    }

    public int getI_modification() {
        return i_modification;
    }

    public int getJ_modification() {
        return j_modification;
    }

}
