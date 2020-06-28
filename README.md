
# hmt-editors-template



Template for creating editorial repositories to use in the Homer Multitext project virtual machine.

The current template is intended for use with the HMT project virtual machine used in summer, 2019, and includes validation of features unique to the Upsilon 1.1 and Venetus B manucripts that are the focus of our work in summer, 2019..



## 2020

- includes codex models for 4 MSS
- new config system isolated in `textConfig` dir
- new pluggable validation/verification in mom2020


## Overview of files and directory layout

-  `collections/validation.cex`.  Records of editorial status of pages you have worked on.
-   `dse`.  Directory for files with DSE records.  Files may be any name ending `.cex` but must begin with the header line `passage#imageroi#surface`
-   `editions/iliad`.  TEI editions of *Iliads* go in this directory.  The template file has the citation structured required by the HMT project.
-   `editions/scholia`.  TEI editions of scholia to the *Iliad* go here.  The template files have the citation structured required by the HMT project.
-  `editions/metsumm`.  TEI editions of metrical  summaries of books of the *Iliad* go here.  The template files have the citation structured required by the HMT project.
-  `header`.  Header files used when compiling your entire repository into a single CEX file.  You should not edit these files.
-   `paleography`.  Directory for files with paleographic observations.  Files may be any name ending `.cex` but must begin with the header line `observation#text#image#comments`.
-   `scripts/mom2019.sc`.  The scala script used in validating and verifying your work.
-   `template-copy-paste`.  Some handy files for copying and pasting hard-to-type characters, a complete block of XML for a new scholion, and other reference material.
-   `validation`.  Reports on validating your work are written to this directory.
