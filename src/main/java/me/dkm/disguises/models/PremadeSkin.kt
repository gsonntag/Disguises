package me.dkm.disguises.models

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

enum class PremadeSkin : ISkin {

    HAZMAT {
        override fun getData(): String {
            return "ewogICJ0aW1lc3RhbXAiIDogMTYxODA5MzA1MTg3NCwKICAicHJvZmlsZUlkIiA6ICI2NGJkNWJjYjNkM2Q0MjI2YTczZWEyM2YyMWZhNTY0NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJrb3Bzb245OSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kZjU4MDkzY2EzZDU1NGE2MmM4ZjM3YThjMGJkZTQ5M2VlMzNiZTA2YmQyOGE5Mzg0NjZmNzg1NGMwODEzZmMwIgogICAgfQogIH0KfQ=="
        }

        override fun getSignature(): String {
            return "lbcuApIbycCSEXFlp9m9I2zJHprCVpw2JOyJs21jR/A3BDGw0z8a8vuKS8wT47u3pwORz/fl47ASgvbjxbW34/CpU4N+RQzLrlMCn1eoI74jOYDc8vj9D1WUaouPHXDqDM99fQe/Xh/qZtoxaewcowmKgKRta7tSrddfk3QG7N14QAbc5OvAmu0NU+Qo4ueRdD9JI9OScfIrBqmvFLfD51WtKuzNRKIuh/Wd596hfHICR/bz+eYcyprFYjXhlL6IAnSm+cQMjyS8O01rJdZBtWZ/Zv4v5bjcL7Em9a86kroquwNxLE7HjRASwFo5oikHylp1O1lOytvdnJ96U/0yxhoDJGnlnasN70nm9NVSPp4S12lqJ+M91UHc4Sx6mdfDtnT8ZtdQIbSCa+aTEcDDsr+ieSEvBocRimdwTQaniMkk1D3lQ7AKUqzgj3CQXPhTK3SQQ1IX7EuyQhgWeZflOWBaQA0gAfzxOb6azTZh9DMmual6YoGRpLF/bsA+vwwEt2sxQKqTwvlkwfgypRiBDzq0AMaKn1ZGBzY431ag9ft0SQ5FK2ORO0EZFaLu4hheY008Qo86cXagUODtokTfBCqg1ax84e+Ts1Zg4EzTDIz9ZWhyB7qJH52Ymo68GQqwXSEIyvTlEGJ+8LADKPF0DTH/l0zbtaREP8URFTCThyo="
        }
    },

    STEVE {
        override fun getData(): String {
            return "ewogICJ0aW1lc3RhbXAiIDogMTYxODEyMDYxMjAxNywKICAicHJvZmlsZUlkIiA6ICJjMDZmODkwNjRjOGE0OTExOWMyOWVhMWRiZDFhYWI4MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfU3RldmUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE0YWY3MTg0NTVkNGFhYjUyOGU3YTYxZjg2ZmEyNWU2YTM2OWQxNzY4ZGNiMTNmN2RmMzE5YTcxM2ViODEwYiIKICAgIH0KICB9Cn0"
        }

        override fun getSignature(): String {
            return "sPG37vMa7kznRzSC4mxO/MHHjbiabEjdkfV8MN65m3Z+R9nc8P42dUBn0CdnI9QKpQ1V+MNQrL1WSnoiqnl6ssHj+MlDb4Nn9+QPqIB+39OOByIvSFm5fH+oU21bP0yg/6caHS/wI1OVVfonKoi/6oxJRX9FhS4BRR5VShlWABxSK9X2/o7cggoprUCXND2bYWHmO/ZwjGzadyBxiilZzk4fHMbf8GaY5yxC8FUmx6U6m4yfs6uSGXlMYKqIt4U9gbz+IUcwzbk1rhs1LAGsgIRQ8ylS0WkilFgYVPLvx8LUTnytIHCiV7k79ToF1YU56bEe1MIn2w3VkhY3lCbZ6zcydU90jNMURJPz4Fjos+C3aDxz7vQxqUuNley9/LU6Zud0VVHB60OQnD3NuALoun37TwndMuBffsVwIXbnSn9c2KJj+jIki7bIcfVpdG0JaeW5dpSY6ToTzCCRIIDmrAn2TNiRYWg2h5T/hEZ/GwEaqJtqVzwVAN3+CstfygzYwy4WO3rbJonXobypYlLsJxqYTmu7gUcOa40/vL5+ap0rWJvQ2BvLxZiyyiza8BOqK6AmMGVDwwiUafF927gXkLlIfN207dDdbJz5aQ5KGNGNft/USNEw3IsFd2jQ3l9iJkxNqyMvEOjzpDaX007uQ7vbxDkVoOWkujYV48B8pBs="
        }
    },

    ALEX {
        override fun getData(): String {
            return "ewogICJ0aW1lc3RhbXAiIDogMTYxODEyMDY2NDAzMywKICAicHJvZmlsZUlkIiA6ICI2YWI0MzE3ODg5ZmQ0OTA1OTdmNjBmNjdkOWQ3NmZkOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJNSEZfQWxleCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS84M2NlZTVjYTZhZmNkYjE3MTI4NWFhMDBlODA0OWMyOTdiMmRiZWJhMGVmYjhmZjk3MGE1Njc3YTFiNjQ0MDMyIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0"
        }

        override fun getSignature(): String {
            return "N+bqz0ygHV9L5TPR4X17Bie9qstOkZbyDDa+YWYGpA4o9mjn2spnyTKafR0i6daInKiXUWv0mv2ZDixofJfMWe0veyp/Tw5vOoqXex+crJQifFHELuEtJxSHdHc3bYX46hWDDuv4OrpTzxjGiG9Rd13gYvsQteTO4IgIOFobuFVDijNLLTRCKa64mSyTqadwO+pRRSodv+MkKr5kzydBtdIaqMfJHVWQ++HfvQVkgcwpPZ4DfaQSrhgfG4ogG6hKUM/VN6FgXl/YEsnrEFHIzQryUNF5BOsN7Oyu45fMwte91BcsBY0RIvWnrGjwKCcN/4rw8rteExxJ5Wy3F5ORlu+jCMh6NmQK/KUK/at3WU7rFTFD7e4KqYqipQCjygpqvxsRhAIoRtZv1KC9+jobZYCl4yMzzjILWUh+Wosh5c36apZ2pZeV2c60CJDdcFbP+EF3Y7HxRpnbaennOA/UJC8/UFF7vQYkR5gFhas8y5Z3l/yaIx+2y9pLVVLvLPN+EkBWMJeDY/SX/BtxxqIffYHGbQCp3S505O8G29XAktcYbI2cCA0ICRvWJlRUbDnXOFfQpxp0YFiJrdMOkyzA1oN4UH7vQi3kko+u/4FxvnLWdbfUh0oUYOiDE/HzdDdpku902UctM4Wvmul2YsDlYbxMRIFIooe38OZCHMCit4k="
        }
    }
    ;
    companion object {
        fun of(str: String): PremadeSkin? {
            return try {
                valueOf(str)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}