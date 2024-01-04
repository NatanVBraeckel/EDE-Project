import { useEffect } from "react";

function Home() {
  useEffect(() => {
    document.title = "Enterprise zoo";
  }, []);

    return(
      <div style={{ margin: "2rem" }}>
        <h2>Enterprise zoo</h2>
  
        <p>Manage the food, animals and enclosures of the zoo!</p>

      </div>
    );
  }
  
  export default Home;