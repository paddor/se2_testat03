SOURCES = report.tex

ba.pdf:	$(SOURCES)
	pdflatex $^
	pdflatex $^

.PHONY: all clean

all:	report.pdf

clean:
	rm -f *.aux *.blg *.out *.bbl *.log *.toc *.bcf *-blx.bib *.run.xml report.pdf
