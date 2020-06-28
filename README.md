
# hmt-editors-template



Template for editorial repositories to use in the Homer Multitext project.

The current template was used in the HMT 2020 Summer Experience.  It is pre-configured for work on the Venetus B and the Escorial, Upsilon 1.1 manuscripts; the `mom2020.sc` script for validation and verification assesses only:

- all data can be assembled into a citable digital library
- all indexed relations in the `dse` directory are validated and verified
- character set usage for *Iliad* editions is validated and verified.


## Using this template outside the 2020 Summer Experience


### Configuring other texts

The template includes codex models for four manuscripts:  in addition to the Venetus B and the Upsilon 1.1, it includes the Venetus and British Library Burney 86  ("the Townley Homer").  To work on any of those four manuscripts, you need to configure entries for your chosen manuscripts' texts in the `.cex` files in the `textConfig` directory.  (If you want to work with other Homeric manuscripts, you'll need to add codex models for them to the `codices-catalog` and `codices-data` directories.)

The four files in `textConfig` are:

- `catalog.cex` provides basic bibligoraphic information about each text
- `citation.cex` defines how the citation scheme maps on to the concrete document.  (These two files provide the information used by the `fromFiles` method of the `TextRespositorySource` object: see [API](https://cite-architecture.github.io/cite-api-docs/ohco2/api/edu/holycross/shot/ohco2/TextRepositorySource$.html).)
- `readers.cex` maps texts onto classes that can generate plain-text editions from XML markup
- `orthographies.cex` maps texts onto classes than can be generate a classified tokenization


### Validation and verification

The validation/verification script `mom2020.sc` is completely restructured in 2020.  It applies instances of the `citevalidator` library's `CiteValidator` trait. (See [user guide](https://cite-architecture.github.io/citevalidator/).)  Implementing classes include a `validate` method that creates structured `TestResult`s; human-readable reports for verification (in markdown format) are generated with the `verify` method.

For the 2020 Summer Experience, only two validators were included:  a generic validation of all indexed relations in the `dse` data set, and validation of the character usage in *Iliad* editions.  It is straightforward to add further validators to the `mom2020.sc` script for teams that are prepared to assess their work in more depth.


- new pluggable validation/verification in mom2020
