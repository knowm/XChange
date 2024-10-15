## Using IntelliJ Idea HTTP client

There are *.http files stored in `src/test/resources/rest` that can be used with IntelliJ Idea HTTP Client.

Some requests need authorization, so the api credentials have to be stored in `http-client.private.env.json` in module's root. Sample content can be found in `example.http-client.private.env.json`

> [!CAUTION]
> Never commit your api credentials to the repository!


[HTTP Client documentation](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html)
