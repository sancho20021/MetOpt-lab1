package methods;

public abstract class Minimizer {
    protected final Function fun;
    protected final double a, b, eps;

    public Minimizer(final Function fun, final double a, final double b, final double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("eps should be > 0");
        }
        this.fun = fun;
        this.a = a;
        this.b = b;
        this.eps = eps;
    }

    public abstract boolean hasNext();

    public abstract Section next();

    public abstract void restart();

    public abstract double findMinimum();
}
