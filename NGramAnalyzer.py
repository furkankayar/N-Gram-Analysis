import os 
import re
from contextlib import contextmanager
from requests.structures import CaseInsensitiveDict
import time
import locale 

locale.setlocale(locale.LC_ALL, 'tr_TR.utf8')
lower_map = {
    ord(u'I'): u'ı',
    ord(u'İ'): u'i',
}

def main():
    file_names = [
        "Novel-Samples\\BİLİM İŞ BAŞINDA.txt",
        "Novel-Samples\\BOZKIRDA.txt", 
        "Novel-Samples\\DEĞİŞİM.txt", 
        "Novel-Samples\\DENEMELER.txt",
        "Novel-Samples\\UNUTULMUŞ DİYARLAR.txt",
    ]
    start_time = time_ms()
    allFiles = read_files(file_names=file_names)
    print("-----------------------------------------------------------------------------------------------------------------------")
    print("Removing punctuations and multiple spaces...")
    print("-----------------------------------------------------------------------------------------------------------------------")
    
    RE_CHARACTERS = re.compile(r"['\u0307\x90-\x99]")
    allFiles = RE_CHARACTERS.sub("", allFiles)
    
    RE_PUNCTUATIONS = re.compile(r"[(),.?!:\"»«\t*;]")
    allFiles = RE_PUNCTUATIONS.sub(" ", allFiles).replace("-", " ")
    RE_MULTIPLE_SPACE = re.compile(r"( )+")
    allFiles = RE_MULTIPLE_SPACE.sub(" ", allFiles).strip()

    one_gram = analyze_ngram(n=1, file=allFiles)
    two_gram = analyze_ngram(n=2, file=allFiles)
    three_gram = analyze_ngram(n=3, file=allFiles)
    
    print_ngrams(one_gram=one_gram, two_gram=two_gram, three_gram=three_gram)
    print("\nExecution completed in", time_ms() - start_time, "milliseconds.", end="\n\n")
    input("Press Enter to continue...")


def read_files(file_names=[]):
    content = ""
    loc = os.path.dirname(os.path.abspath(__file__))
    start_time = time_ms()
    for file_name in file_names:
        file_name = os.path.join(loc, file_name)
        print("Reading ", file_name)
        with open_with_error(file_name, encoding="iso-8859-9") as (f, err):
            if err:
                print("IOError:", err)
            else:
                RE_CRLF = re.compile(r"[\n\r]")
                content += f.read()
                content = lower_locale(RE_CRLF.sub(" ", content))
                
    print("\n", len(file_names), " files has been read in ", time_ms() - start_time, " milliseconds.", sep="")
    if len(content) == 0:
        print("\nThere is not any word to analyze!\nPLEASE PUT TXT FILES IN Novel-Samples FOLDER!!!!\n")
        input("Press Enter to continue...")
        exit()
    return content


def analyze_ngram(n=1, file=""):
    print("Generating ", n, "-gram .... ", sep="", end="")
    words = file.split(" ")
    n_grams = CaseInsensitiveDict()
    for i in range(n-1, len(words)):
        n_gram = ""
        for k in range(i, i-n, -1):
            n_gram = (words[k] + " " + n_gram).strip()
        if n_grams.get(n_gram) is not None:
            n_grams[n_gram] += 1
        else:
            n_grams[n_gram] = 1
    print("Sorting .... ", end="")
    start_time = time_ms()
    sorted_n_grams = sorted(n_grams.items(), key=lambda x: x[1], reverse=True)
    print("Completed in", time_ms() - start_time, "milliseconds.")
    return sorted_n_grams


def print_ngrams(one_gram=None, two_gram=None, three_gram=None):
    
    print("-----------------------------------------------------------------------------------------------------------------------")
    print("\n%6s   %-14s%14s       %-14s%14s       %-19s%19s" % (" ", "1-Gram", "Frequency", "2-Gram", "Frequency", "3-Gram", "Frequency"))
    print("-----------------------------------------------------------------------------------------------------------------------")
    for i in range(0, 100):
        print("%3d. |   %-23s %4d   |   %-23s %4d   |   %-33s %4d " % ( 
                i+1, 
                one_gram[i][0], 
                one_gram[i][1], 
                two_gram[i][0], 
                two_gram[i][1], 
                three_gram[i][0], 
                three_gram[i][1]
            ))
    print("\nTotal number of 1-grams:", len(one_gram), "\tTotal frequency of 1-grams:", sum([pair[1] for pair in one_gram]))
    print("Total number of 2-grams:", len(two_gram), "\tTotal frequency of 2-grams:", sum([pair[1] for pair in two_gram]))
    print("Total number of 3-grams:", len(three_gram), "\tTotal frequency of 3-grams:", sum([pair[1] for pair in three_gram]))
    print("\n-----------------------------------------------------------------------------------------------------------------------")


def time_ms(): 
    return int(time.time_ns() / 1000000)


def lower_locale(text):
    text = text.translate(lower_map)
    return text.lower()

@contextmanager
def open_with_error(filename, mode="r", encoding="utf-8"):
    try: 
        f = open(filename, mode, encoding=encoding)
    except IOError as err:
        yield None, err
    else:
        try:
            yield f, None
        finally:
            f.close()


if __name__ == "__main__":
    main()