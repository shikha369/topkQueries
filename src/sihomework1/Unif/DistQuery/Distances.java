/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.Unif.DistQuery;

/**
 *
 * @author cool
 */
public class Distances {

    private Double maxL1Norm;
    private Double minL1Norm;
    private Double maxL2Norm;
    private Double minL2Norm;
    private Double maxInfNorm;
    private Double minInfNorm;

    public Distances() {
        maxL1Norm = Double.MIN_VALUE;
        maxL2Norm = Double.MIN_VALUE;
        maxInfNorm = Double.MIN_VALUE;
        minL1Norm = Double.MAX_VALUE;
        minL2Norm = Double.MAX_VALUE;
        minInfNorm = Double.MAX_VALUE;
    }

    public double get(String norm) {
        switch (norm) {
            case "maxL1Norm":
                return maxL1Norm;
            case "minL1Norm":
                return minL1Norm;
            case "maxL2Norm":
                return maxL2Norm;
            case "minL2Norm":
                return minL2Norm;
            case "maxInfNorm":
                return maxInfNorm;
            case "minInfNorm":
                return minInfNorm;
            default:
                return (Double.MAX_VALUE);
        }
    }

    public void Update(String norm, Double val) {
        switch (norm) {
            case "maxL1Norm":
                this.maxL1Norm = val;
                break;
            case "minL1Norm":
                this.minL1Norm = val;
                break;
            case "maxL2Norm":
                this.maxL2Norm = val;
                break;
            case "minL2Norm":
                this.minL2Norm = val;
                break;
            case "maxInfNorm":
                this.maxInfNorm = val;
                break;
            case "minInfNorm":
                this.minInfNorm = val;
                break;
        }

    }
}
