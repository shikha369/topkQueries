/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sihomework1.AggQuerySquentialScanning;

/**
 *
 * @author cool
 */
public class DataPoint {
    /*Datastructure to store datapoint with 2 attributes*/

    private String objectName;
    private Double attribute1;
    private Double attribute2;

    public void setAttribute1(Double val) {
        this.attribute1 = val;
    }

    public void setAttribute2(Double val) {
        this.attribute2 = val;
    }

    public void setObjectName(String val) {
        this.objectName = val;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public Double getAttribute1() {
        return this.attribute1;
    }

    public Double getAttribute2() {
        return this.attribute2;
    }
}
