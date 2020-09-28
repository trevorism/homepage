<template>
  <div id="prototype">
    <HeaderBar></HeaderBar>
    <div class="container">
      <div class="title mt-6">
        Prototype Driven Development
      </div>

      <section class="content">
        May 2013
      </section>

      <section class="content">
        You get a requirement for a new feature or enhancement. Many times, this includes
        a complex change to the code base, or necessitates the inclusion of some new frameworks or libraries.
        When this is the case, some developers begin by writing code, hoping whatever springs to mind will work.
        Of course, this approach is wrong, and it blows up in programmers' faces in the form of a lot of
        deleted code and wasted effort. I hope to convince you that coding first, in a specific way, can reap huge
        benefits.
      </section>

      <section class="content">
        A lot of smart people have tried to code complex software solutions without a
        design and failed. Unquestionably, many of the problems we face are much more difficult to solve than they
        appear on the surface. So even smarter people have come up with software methodologies, which force
        the developer into a design step. Waterfall, Test Driven Development(TDD), Rational Unified
        Process(RUP),
        Feature Driven Development(FDD) are all contrasting schools of thought on the development process,
        but each includes a design step. While there are these are popular, and there are many others, I submit
        a new one: Prototype Driven Development(PDD).
      </section>

      <section class="content">
        <h4>PDD Process</h4>
      </section>

      <section class="content">
        <h4>PDD Process</h4>
        <ol>
          <li>Design the prototype</li>
          <li>Develop the prototype</li>
          <li>Use the prototype as a tool during the traditional design phase</li>
          <li>Incrementally improve the prototype</li>
          <li>Repeat as necessary ;)</li>
        </ol>
      </section>

      <section class="content">
        <h4>Prototype Design</h4>
      </section>

      <section class="content">
        Designing a complex module on paper or using
        UML does not get the code closer to a finished product, and in nearly
        all cases cannot be completed accurately in a reasonable amount of time. Designing the
        prototype should be a quick, painless process. Choose the technologies, look for obvious
        pitfalls, and think about how to get a prototype produced quickly.
      </section>

      <section class="content">
        <h4>Prototype Development</h4>
      </section>

      <section class="content">
        Developing a prototype from the design should also be a quick
        process. At this step, a programmer may encounter some subtle issues that need to be addresses, but
        these can be largely ignored unless they block the prototype's development. Keep in mind, the
        goal of developing the prototype is to use as an aid for the final product. Focus on speed.
      </section>

      <section class="content">
        <h4>Use the Prototype</h4>
      </section>

      <section class="content">
        With the prototype in hand, one can use it in a variety of beneficial
        ways. One immediate benefit is that it can be used as a platform for discussion of
        requirements and design. The knowledge gained by developing the prototype can be used to
        determine the validity of the requirements, and major and minor pitfalls with the
        current prototype's design. We can answer questions like: Will the current design scale? Is it
        testable? Another use is for estimation. The prototype developer can often accurately
        estimate how long it will take to satisfy the requirements.
      </section>

      <section class="content">
        In an ideal scenario, the design of the prototype scales well, and a development
        team  can incrementally improve the prototype until its completed. If design discussion yields some issues
        with the prototype, I suggest first redesigning the prototype, then repeating the PDD steps. When
        you  are happy with your prototype design, you can use you favorite established development methodology,
        (e.g TDD) to complete the module. It is at this stage that you can focus on using the technologies the
        right way, making the code clean and elegant. Development becomes almost too easy by employing this
        strategy.
      </section>

    </div>
  </div>
</template>

<script>
import HeaderBar from '../HeaderBar'

export default {
  name: 'Prototype',
  components: {HeaderBar}
}
</script>

<style scoped>

</style>
