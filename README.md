# QuickBuilder

Builders without boiler plate. 

## Rationale

The builder pattern helps keep tests readable and maintainable but requires tedious boilerplate. QuickBuilder lets you spend more time on your real code, and less writing boilerplate, by generating fully featured builders on the fly. 

You supply an interface, QuickBuilder supplies an implementation.

QuickBuilder can create builders for pretty much any class - it cleanly handle immutable types and other classes not following the bean convention without resorting to dirty tricks like setting fields via reflection.

## 60 second quickstart

For any bean

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

And use it like any other builder

```java
    Person p = QB.builder(PersonBuilder.class).withName("Bob").withAge(42).build();
```

For non beans you can supply a "seed" function to create the type

```java
 class Seed implements Generator<Person, PersonBuilder> {
    @Override
    public FruitBean generate(PersonBuilder builder) {
      return Person.create();
    }
 }

 PersonBuilder pb = QB.builder(PersonBuilder.class, new Seed());
```

You can declare that you will take responsibility for a property by declaring an underscore method in your builder.

```java
interface PersonBuilder extends Builder<Person> {
  PersonBuilder withName(String name);
  PersonBuilder withAge(int age);
  PersonBuilder withPartner(PersonBuilder partner);

  String _Name();
}
```

And then use this method in your seed function to route the value to the right place.

```java
 class Seed implements Generator<Person, PersonBuilder> {
    @Override
    public FruitBean generate(PersonBuilder builder) {
      return Person.create(builder._Name());
    }
 }
```


## Features

* Creates builders for beans by defining interface only
* Creates builders for non-beans (eg immutable classes) by defining interface and small seeder function
* Builder methods may take normal types or other builders as parameters
* Builders return shallow copies of themselves - no need for special "but" methods

## Design principles

1. Well behaved - only public interface of built type used
2. Non invasive - no changes or annotations needed on built type
3. Fail as early as possible - error on type creation or compile if possible

## TODO

* Support any lower case prefix for properties
* Seperate interface for underscore methods
* Collect list entries individually
