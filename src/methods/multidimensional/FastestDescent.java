package methods.multidimensional;

import methods.unidimensional.CombinationMinimizer;
import methods.unidimensional.FibonacciMinimizer;
import methods.unidimensional.Minimizer;
import models.Vector;
import models.functions.QuadraticFunction;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

public class FastestDescent extends MultiMinimizer {
    private Vector x;
    private final double maxA;
    private final Class<? extends Minimizer> uniMinimizer;

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps, double maxA) {
        this(fun, startX, eps, maxA, FibonacciMinimizer.class);
    }

    public FastestDescent(QuadraticFunction fun, Vector startX, double eps, double maxA, Class<? extends Minimizer> uniMinimizer) {
        super(fun, startX, eps);
        this.maxA = maxA;
        this.uniMinimizer = uniMinimizer;
        restart();
    }

    @Override
    public boolean hasNext() {
        return fun.getGradient(x).getEuclideanNorm() >= eps;
    }

    @Override
    protected Vector nextIteration() {
        return x = x.add(fun.getGradient(x).multiply(-oneDimMin(x)));
    }

    @Override
    public void restart() {
        x = startX;
    }

    @Override
    public Vector getCurrentXMin() {
        return x;
    }

    private double oneDimMin(Vector x0) {
        Function<Double, Double> uniFunction = a -> fun.get(x0.add(fun.getGradient(x0).multiply(-a)));
        // printUniFunction(uniFunction);
        try {
            return uniMinimizer
                    .getConstructor(Function.class, double.class, double.class, double.class)
                    .newInstance(uniFunction, 0.0, maxA, eps)
                    .findMinimum();
        } catch (Exception e) {
            System.err.println("Error occurred while trying to use one dimension minimizer");
            throw new IllegalStateException("See log, message: " + e.getMessage(), e);
        }
    }

    private void printUniFunction(Function<Double, Double> uniFunction) {
        for (double xx = 0; xx < maxA; xx = (xx + 0.5)*1.1) {
            System.out.println("(x, f(x)) = (" + xx + ", " + uniFunction.apply(xx) + ")");
        }
    }

    public void testDirection(Vector x0) {
        var gradient = fun.getGradient(x0);
        System.out.println("Gradient: " + gradient);
    }
}
