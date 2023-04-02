package com.diploma.project.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;

public class JpaSpecificationBuilder<Entity> {

    private Specification<Entity> specification;

    public JpaSpecificationBuilder() {
    }

    public Specification<Entity> getSpecification() {
        return specification;
    }

    public JpaSpecificationBuilder(Specification<Entity> specification) {
        this.specification = specification;
    }

    public JpaSpecificationBuilder<Entity> equal(String field, Object o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.equal(root.get(field), o));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> notEqual(String field, Object o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.notEqual(root.get(field), o));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> equal(Expression<?> expression, Object o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.equal(expression, o));
        }
        return this;
    }


    public JpaSpecificationBuilder<Entity> equal(Expression<?> expression, Expression<?> o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.equal(expression, o));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> equalIgnoreCase(String field, String o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.equal(cb.lower(root.get(field)), o.toLowerCase()));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> equalIgnoreCase(Expression<?> expression, String o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.equal(expression, o.toLowerCase()));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> like(String field, String o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.like(cb.lower(root.get(field)), "%" + o.toLowerCase() + "%"));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> like(Expression<String> expression, String o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.like(expression, "%" + o.toLowerCase() + "%"));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> in(String field, Object o) {
        if (o != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.in(root.get(field)).value(o));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> isNull(String field) {
        specification = andSpecification(specification, (root, query, cb) ->
                cb.isNull(root.get(field)));
        return this;
    }

    public JpaSpecificationBuilder<Entity> isNotNull(String field) {
        specification = andSpecification(specification, (root, query, cb) ->
                cb.isNotNull(root.get(field)));
        return this;
    }

    public <Y extends Comparable<? super Y>> JpaSpecificationBuilder<Entity> between(String field, Y start, Y end) {
        if (start != null && end != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.between(root.get(field), start, end));
        }
        return this;
    }

    public <Y extends Comparable<? super Y>> JpaSpecificationBuilder<Entity> between(Expression<? extends Y> expression, Y start, Y end) {
        if (start != null && end != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.between(expression, start, end));
        }
        return this;
    }

    public <Y extends Comparable<? super Y>> JpaSpecificationBuilder<Entity> between(String field, Expression<? extends Y> start, Expression<? extends Y> end) {
        if (start != null && end != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.between(root.get(field), start, end));
        }
        return this;
    }

    public <Y extends Comparable<? super Y>> JpaSpecificationBuilder<Entity> between(Expression<? extends Y> expression, Expression<? extends Y> start, Expression<? extends Y> end) {
        if (start != null && end != null) {
            specification = andSpecification(specification, (root, query, cb) ->
                    cb.between(expression, start, end));
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> addSpecification(Specification<Entity> newSpecification) {
        if (newSpecification != null) {
            specification = andSpecification(specification, newSpecification);
        }
        return this;
    }

    public JpaSpecificationBuilder<Entity> add_OR_Specification(Specification<Entity> newSpecification) {
        if (newSpecification != null) {
            specification = orSpecification(specification, newSpecification);
        }
        return this;
    }

    private Specification<Entity> andSpecification(Specification<Entity> src, Specification<Entity> newSpecification) {
        return src == null
                ? Specification.where(newSpecification)
                : src.and(newSpecification);
    }

    private Specification<Entity> orSpecification(Specification<Entity> src, Specification<Entity> newSpecification) {
        return src == null
                ? Specification.where(newSpecification)
                : src.or(newSpecification);
    }

    public Specification<Entity> build() {
        return specification;
    }
}
