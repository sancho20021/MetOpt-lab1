package methods.multidimensional;

public class GradientDescentMinimizer extends MultiMinimizer {
    private final double startAlpha;
    private double alpha;
    private Vector x;

    public GradientDescentMinimizer(final QuadraticFunction fun, double startAlpha, Vector startX, double eps) {
        super(fun, startX, eps);
        this.startAlpha = startAlpha;
        restart();
    }

    public boolean hasNext() {
        return fun.getGradient(x).getEuclideanNorm() >= eps;
    }

    public Vector nextIteration() {
        Vector next = x.add(fun.getGradient(x).normalize().multiply(-alpha));
        if (fun.get(next) < fun.get(x)) {
            return x = next;
        }
        alpha /= 2;
        return x;
    }

    @Override
    public void restart() {
        alpha = startAlpha;
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

}
