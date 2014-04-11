[![Build Status](https://travis-ci.org/hcoles/QuickBuilder.svg?branch=master)](https://travis-ci.org/hcoles/QuickBuilder)

# QuickBuilder

Builders without boiler plate. 

## Rationale

The builder pattern helps keep tests readable and maintainable but requires tedious boilerplate. QuickBuilder lets you spend more time on your real code, and less writing boilerplate, by generating fully featured builders on the fly. 

You supply an interface, QuickBuilder supplies an implementation.

QuickBuilder can create builders for pretty much any class - it cleanly handle immutable types and other classes not following the bean convention without resorting to dirty tricks like setting fields via reflection.

## 60 second quickstart

For any bean e.g.

```java
public class Person {
    private String name;
    private int age;
    private Person partner;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPartner(Person partner) {
        this.partner = partner;
    }

    // etc
}
```

Create a builder by declaring an interface

```java
interface PersonBuilder extends Builder<Person> {
  PersonBuilder withName(String name);
  PersonBuilder withAge(int age);
  PersonBuilder withPartner(PersonBuilder partner);
}

```

Get an instance by calling QB.builder, then use it like any other builder

```java
    Person p = QB.builder(PersonBuilder.class).withName("Bob").withAge(42).build();
```

For classes without a public non no-args constructor you can supply a "seed" function that creates the instance.

```java
 class Seed implements Generator<Person, PersonBuilder> {
    @Override
    public Person generate(PersonBuilder builder) {
      return Person.create();
    }
 }

 // pass an instance of the seed function to QuickBuilder
 PersonBuilder pb = QB.builder(PersonBuilder.class, new Seed());
```

For classes that don't provide setter methods (e.g immutable classes) you can tell QuickBuilder that you will take responsibility for a property by declaring an underscore method in your builder.

```java
interface PersonBuilder extends Builder<Person> {
  PersonBuilder withName(String name);
  PersonBuilder withAge(int age);
  PersonBuilder withPartner(PersonBuilder partner);

  // underscore method telling QuickBuilder not to handle this property
  String _Name();
}
```

The underscore method can then be used in your seed function to route the value to the right place.

```java
 class Seed implements Generator<Person, PersonBuilder> {
    @Override
    public Person generate(PersonBuilder builder) {
      return Person.create(builder._Name());
    }
 }
```


## Features

* Automatically creates builders for beans - you just supply an interface
* Creates builders for non-beans (e.g. immutable classes) by defining interface and small seeder function
* Builder methods may take normal types or other builders as parameters
* Builders return shallow copies of themselves - no need for special "but" methods
* Supports any lowercase prefix for property methods eg "withName", "andName", "usingName"

## Design principles

1. Well behaved - only public interface of built type used
2. Non invasive - no changes or annotations needed on built type
3. Fail as early as possible - error on type creation or compile if possible

## TODO

* Separate interface for underscore methods
* Collect list entries individually
* Auto generation for classes with single factory method
* Auto generation for classes with constructor without repeated types

## Alternatives

Other approaches you might want to consider

* https://code.google.com/p/make-it-easy/ 
* http://code.google.com/a/eclipselabs.org/p/bob-the-builder/

