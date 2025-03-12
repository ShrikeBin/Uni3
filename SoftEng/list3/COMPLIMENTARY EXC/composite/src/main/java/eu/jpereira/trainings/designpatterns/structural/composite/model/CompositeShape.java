/**
 * Copyright 2011 Joao Miguel Pereira
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.jpereira.trainings.designpatterns.structural.composite.model;

import eu.jpereira.trainings.designpatterns.structural.composite.model.Shape;
import eu.jpereira.trainings.designpatterns.structural.composite.model.ShapeType;
import eu.jpereira.trainings.designpatterns.structural.composite.model.ShapeDoesNotSupportChildren;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// create abstract class CompositeShape that extends Shape to use as a base for composite shapes

public abstract class CompositeShape extends Shape {

    List<Shape> shapes;

    public CompositeShape() {
        this.shapes = createShapesList();
    }

    /**
     * Remove a shape from this shape's children.
     * 
     * @param shape the shape to remove
     * @return true if the shape was present and was removed, false if the shape was not present
     */
    public boolean removeShape(Shape shape) {
        return shapes.remove(shape);
    }

    /**
     * Return the total shapes count in case this is a composite
     * 
     * @return the total count of shapes if the shape is composite. -1 otherwise
     */
    public int getShapeCount() {
        return shapes.size();
    }

    /**
     * Add a shape to this composite shape.
     * 
     * @param shape the shape to add
     */
    public void addShape(Shape shape) {
        if (this instanceof CompositeShape) {
            shapes.add(shape);
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    /**
     * Returns a list of shapes filtered by their type.
     * 
     * @param type the type of shape to filter
     * @return a list of shapes that match the given type
     */
    public List<Shape> getShapesByType(ShapeType type) {
        return shapes.stream()
                .filter(shape -> shape.getType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Return all shapes that are leaves in the tree.
     * 
     * @return a list of leaf shapes
     */
    public List<Shape> getLeafShapes() {
        return shapes.stream()
                .filter(shape -> !(shape instanceof CompositeShape))
                .collect(Collectors.toList());
    }

    /**
     * Factory method for creating a list of child shapes. Can be overridden.
     * 
     * @return a new empty list of shapes
     */
    protected List<Shape> createShapesList() {
        return new ArrayList<>();
    }

    /**
     * If this object is a CompositeShape, return it. Null otherwise.
     * 
     * @return the composite shape instance if this is a composite, null otherwise
     */
    @Override
    public CompositeShape asComposite() {
        return this;
    }
}
