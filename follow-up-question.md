# Follow up questions

1. **How long did you spend on the test? What would you add if you had more time?**
   - *Response:* 
     I spent 12 hours on the test. If I had more time, I would have focused on implementing additional functionalities such as enhanced support for adding Products with different structures, persistence, user authentication and session management, and adding more features to the shopping cart. Additionally, I would have created a Dockerfile for containerization.

2. **What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that shows how you've used it.**
   
3. **What did you find most difficult?**
   - *Response:* 
     The most challenging aspect was working with technologies with which I hadn't previously implemented, such as Flux/Reactive Spring. Another challenge was avoiding overengineering and focusing solely on delivering the requested basic functionalities.

4. **What mechanism did you put in place to track down issues in production on this code? If you didn’t put anything, write down what you could do.**
   - *Response:* 
     I implemented exception handling, capturing, and logging with informative messages about potential issues. This mechanism helps in tracking down and resolving issues in production efficiently.

5. **The Wiremock represents one source of information. We should be prepared to integrate with more sources. List the steps that we would need to take to add more sources of items with different formats and promotions.**
   
   **Adding new sources of items:**
   - Register a new item source.
   - Add the new service URL in the APIs section in the `application.yaml` file.
   - Configure the beans for new product sources in the `WebClientConfig`.
   - Implement new product services by implementing the `ProductService` interface.
   - Modify the `CartService` to handle possible different types of Products if they have a different structure.
   
   **Adding new promotions:**
   - Create a new `@Service` in `strategies.promotions` that implements the `PromotionStrategy` interface.
   - Automatically, the `CartService` will apply the promotion for the specified type.
